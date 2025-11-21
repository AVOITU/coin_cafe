<?php
// --------- CONFIGURATION ---------
// TO DO :
$host = "127.0.0.1";      // ou 127.0.0.1
$user = "tysta1713984_6an8nq";           // ton utilisateur MySQL
$pass = "TyStand29@";               // mot de passe MySQL
$db   = "tysta1713984_6an8nq"; // remplace par ta base
// ---------------------------------

// Test de connexion
$conn = new mysqli($host, $user, $pass, $db);

// Vérifier la connexion
if ($conn->connect_error) {
    die("<strong style='color:red;'>❌ Erreur de connexion MySQL :</strong> " . $conn->connect_error);
} else {
    echo "<strong style='color:green;'>✅ Connexion réussie à la base de données :</strong> <em>$db</em><br>";
}

// (Optionnel) Vérifier si la base contient des tables
$result = $conn->query("SHOW TABLES");
if ($result) {
    echo "📂 <u>Tables trouvées :</u><br>";
    while ($row = $result->fetch_array()) {
        echo "• " . $row[0] . "<br>";
    }
} else {
    echo "⚠ Impossible d'afficher les tables.";
}

$conn->close();

