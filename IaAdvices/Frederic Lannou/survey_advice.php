<?php
/**
 * survey_advice.php (OpenAI uniquement)
 *
 * Reçoit en POST un JSON de catégories :
 * [
 *   {"label": "Accueil", "value": 3.8},
 *   {"label": "Ambiance", "value": 4.2}
 * ]
 *
 * Pour chaque catégorie :
 * - génère un prompt adapté au score,
 * - appelle l'API Responses (OpenAI) en JSON Mode (json_object) ou Structured Outputs (json_schema),
 * - renvoie un JSON exploitable côté front / Java.
 */

/*******************************
 * Configuration
 *******************************/
$CONFIG = [
    'openai' => [
        'api_key'  => getenv('OPENAI_API_KEY') ?: 'METS_ICI_TA_CLE_OPENAI_PAYANT',
        'base_url' => 'https://api.openai.com/v1/responses',
        'model'    => 'gpt-4.1-mini', // ou 'gpt-4.1'
    ],

    // JSON mode (json_object) ou JSON Schema strict
    'use_json_schema' => false,

    // Intensité par seuil
    'thresholds' => [
        'faible' => 4.20,
        'ciblee' => 3.80,
        'forte'  => 3.50,
        // < 3.50 => tres_forte
    ],
];

/*******************************
 * Lecture des catégories depuis le JSON POST
 *******************************/
$rawInput = file_get_contents('php://input');
$data = json_decode($rawInput, true);

if (!is_array($data)) {
    http_response_code(400);
    header('Content-Type: application/json; charset=utf-8');
    echo json_encode(['error' => 'Payload JSON invalide'], JSON_UNESCAPED_UNICODE);
    exit;
}

// $rows = tableau d’objets {label, value}
$rows = $data;
$surveyId = 1; // fixe ou récupéré d’un champ JSON si tu veux plus tard

/*******************************
 * Fonctions d’aide
 *******************************/
function intensiteAmelioration(float $val, array $thresholds): array {
    if ($val >= $thresholds['faible']) return ['faible',      'Maintenir l’existant et optimiser à la marge'];
    if ($val >= $thresholds['ciblee']) return ['ciblee',      'Cibler quelques irritants précis'];
    if ($val >= $thresholds['forte'])  return ['forte',       'Corriger des problèmes significatifs'];
    return                                ['tres_forte', 'Plan d’actions prioritaire et structurant'];
}

function buildUserPrompt(string $label, float $value, array $thresholds): string {
    [$niveau, $desc] = intensiteAmelioration($value, $thresholds);

    $cadreCommun = <<<TXT
Contexte : librairie avec coin café. Nous exploitons un sondage clients par thème.
Thème : "$label"
Note moyenne (sur 5) : {$value}

Objectif : proposer des pistes d'amélioration **concrètes et réalistes** pour ce thème, adaptées à l’intensité d’effort à fournir.
Contraintes générales :
- Tenir compte d’un budget limité (prioriser ROI, coûts estimatifs bas / moyens / élevés).
- Donner des actions opérationnelles, testables en moins de 30 jours quand c’est possible.
- Inclure des métriques de suivi (ex: % incidents, temps d’attente, NPS par thème, ventes café, etc.).
TXT;

    switch ($niveau) {
        case 'faible':
            $attentes = "Attentes : l’expérience est déjà bonne. Suggère 3 'quick wins' peu coûteux pour maintenir l’excellence et prévenir la régression.";
            break;
        case 'ciblee':
            $attentes = "Attentes : proposer 3–5 améliorations ciblées, dont au moins 2 testables rapidement (prototype/pilote).";
            break;
        case 'forte':
            $attentes = "Attentes : proposer 5 actions à fort impact (dont 2 immédiates à faible coût), plus 1–2 chantiers structurants à planifier.";
            break;
        default: // très forte
            $attentes = "Attentes : prioriser un plan d’actions en 3 phases (immédiat <30j, court terme 1–3 mois, moyen terme 3–6 mois) avec impacts attendus.";
    }

    $format = <<<JSON
{
  "priorite": "faible|ciblee|forte|tres_forte",
  "resume": "2-3 phrases sur la situation et la stratégie",
  "actions_rapides": [
    {"intitule": "...", "description": "...", "cout_estime": "bas|moyen|eleve", "delai": "jours/semaines", "metrique": "..."}
  ],
  "chantiers": [
    {"intitule": "...", "description": "...", "effort": "bas|moyen|eleve", "echeance": "1-3 mois|3-6 mois", "indicateur_succes": "..."}
  ]
}
JSON;

    return $cadreCommun . "\n\n" . $attentes . "\n\n" . $format;
}

/**
 * Construit le bloc "text.format" pour le payload Responses.
 */
function buildTextFormat(bool $useSchema): array {
    if (!$useSchema) {
        return [
            "format" => [
                "type" => "json_object" // JSON Mode
            ]
        ];
    }

    // JSON Schema strict (Structured Outputs)
    return [
        "format" => [
            "type" => "json_schema",
            "json_schema" => [
                "name" => "survey_advice",
                "strict" => true,
                "schema" => [
                    "type" => "object",
                    "required" => ["priorite","resume","actions_rapides","chantiers"],
                    "properties" => [
                        "priorite" => [
                            "type" => "string",
                            "enum" => ["faible","ciblee","forte","tres_forte"]
                        ],
                        "resume" => ["type" => "string"],
                        "actions_rapides" => [
                            "type" => "array",
                            "items" => [
                                "type" => "object",
                                "required" => ["intitule","description","cout_estime","delai","metrique"],
                                "properties" => [
                                    "intitule" => ["type" => "string"],
                                    "description" => ["type" => "string"],
                                    "cout_estime" => ["type" => "string", "enum" => ["bas","moyen","eleve"]],
                                    "delai" => ["type" => "string"],
                                    "metrique" => ["type" => "string"]
                                ]
                            ]
                        ],
                        "chantiers" => [
                            "type" => "array",
                            "items" => [
                                "type" => "object",
                                "required" => ["intitule","description","effort","echeance","indicateur_succes"],
                                "properties" => [
                                    "intitule" => ["type" => "string"],
                                    "description" => ["type" => "string"],
                                    "effort" => ["type" => "string", "enum" => ["bas","moyen","eleve"]],
                                    "echeance" => ["type" => "string"],
                                    "indicateur_succes" => ["type" => "string"]
                                ]
                            ]
                        ]
                    ]
                ]
            ]
        ]
    ];
}

/**
 * Appel HTTP à l’API Responses (OpenAI).
 */
function callResponsesAPI(array $CONFIG, string $userPrompt): array {
    $system = "Tu es un consultant en expérience client spécialisé dans les librairies et cafés. " .
        "Sois pragmatique, franc, et oriente la réponse vers des actions mesurables.";

    $textFormat = buildTextFormat((bool)$CONFIG['use_json_schema']);

    $payload = [
        "model" => $CONFIG['openai']['model'],
        "input" => [
            [
                "role" => "system",
                "content" => [
                    ["type" => "input_text", "text" => $system]
                ]
            ],
            [
                "role" => "user",
                "content" => [
                    ["type" => "input_text", "text" => $userPrompt]
                ]
            ]
        ],
        "text" => $textFormat
    ];

    $headers = [
        'Content-Type: application/json',
        'Authorization: ' . 'Bearer ' . $CONFIG['openai']['api_key']
    ];

    $ch = curl_init($CONFIG['openai']['base_url']);
    curl_setopt_array($ch, [
        CURLOPT_POST => true,
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_HTTPHEADER => $headers,
        CURLOPT_POSTFIELDS => json_encode($payload, JSON_UNESCAPED_UNICODE)
    ]);

    $raw = curl_exec($ch);
    if ($raw === false) {
        $err = curl_error($ch);
        curl_close($ch);
        throw new RuntimeException('cURL error: '.$err);
    }
    $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close($ch);

    if ($httpCode >= 400) {
        throw new RuntimeException("API HTTP $httpCode: $raw");
    }

    $json = json_decode($raw, true);

    // Extraction robuste de la sortie
    $text   = $json['output_text'] ?? null;
    $parsed = null;

    if (!$text && !empty($json['output'][0]['content'])) {
        foreach ($json['output'][0]['content'] as $part) {
            if (($part['type'] ?? null) === 'output_text' && isset($part['text'])) {
                $text = $part['text'];
            }
            if (isset($part['parsed'])) {
                $parsed = $part['parsed']; // présent en json_schema strict
            }
        }
    }

    if (!$parsed) {
        // JSON mode (json_object) → parse du texte
        if ($text) {
            $try = json_decode($text, true);
            if (json_last_error() === JSON_ERROR_NONE) {
                $parsed = $try;
            } elseif (preg_match('/\{.*\}/s', $text, $m)) {
                $try = json_decode($m[0], true);
                if (json_last_error() === JSON_ERROR_NONE) $parsed = $try;
            }
        }
    }

    return [
        'raw'  => $text ?? $raw,
        'json' => $parsed
    ];
}

/*******************************
 * Boucle : on interroge le modèle pour chaque label
 *******************************/
$results = [];

foreach ($rows as $r) {
    // On accepte soit ["label" => "...", "value" => 3.8] soit ["theme" => "...", "score" => 3.8] si tu adaptes côté Java
    $label = $r['label'] ?? ($r['theme'] ?? null);
    $value = isset($r['value']) ? (float)$r['value'] : (float)($r['score'] ?? 0.0);

    if ($label === null) {
        $results[] = [
            'label' => null,
            'value' => $value,
            'error' => 'Entrée sans label'
        ];
        continue;
    }

    $userPrompt = buildUserPrompt($label, $value, $CONFIG['thresholds']);

    try {
        $resp = callResponsesAPI($CONFIG, $userPrompt);
        $results[] = [
            'label'  => $label,
            'value'  => $value,
            'advice' => $resp['json'] ?: $resp['raw']
        ];
        usleep(150000); // petite pause si beaucoup de thèmes
    } catch (Throwable $e) {
        $results[] = [
            'label' => $label,
            'value' => $value,
            'error' => $e->getMessage()
        ];
    }
}

/*******************************
 * Sortie
 *******************************/
header('Content-Type: application/json; charset=utf-8');
echo json_encode([
    'survey_id' => $surveyId,
    'provider'  => 'openai',
    'model'     => $CONFIG['openai']['model'],
    'schema'    => $CONFIG['use_json_schema'] ? 'json_schema(strict)' : 'json_object',
    'results'   => $results
], JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);