// --------- Données d'exemple (REMPLACER par vos vraies données) ---------
const survey = {
    globalRating: 3.8,  // sur 5
    pieCounts: { 1: 8, 2: 12, 3: 22, 4: 28, 5: 30 },
    timeline: {
        labels: ["mois 1","mois 2","mois 3","mois 4","mois 5"],
        values:  [19, 27, 23, 31, 34].map(v=> (v/10)) // converti sur échelle 0..5
    },
    categories: {
        labels: ["Hygiène","Accueil","Ambiance","Accessibilité","Signalétique","Service","Produits","Qualité/Prix","Diversité","Wi‑Fi"],
        values: [3.9,4.2,3.7,3.8,3.4,4.1,3.6,3.8,3.2,3.5]
    },
    byQuestion: {
        labels: ["Q1","Q2","Q3","Q4","Q5","Q6","Q7","Q8","Q9","Q10"],
        values: [3.9,4.2,3.7,3.8,3.4,4.1,3.6,3.8,3.2,3.5]
    }
};
// -----------------------------------------------------------------------

document.getElementById('year').textContent = new Date().getFullYear();
document.getElementById('globalRatingText').textContent = survey.globalRating.toFixed(1) + ' / 5';

// Palette neutre (Chart.js générera des couleurs par défaut si vide)
const mkTicks = (max=5)=>({ beginAtZero:true, suggestedMax:max, stepSize:1 });

// ---- Gauge (Doughnut avec valeur et reste) ----
const gauge = new Chart(document.getElementById('gaugeChart'), {
    type: 'doughnut',
    data: {
        labels: ['Note','Reste'],
        datasets: [{ data: [survey.globalRating, 5 - survey.globalRating] }]
    },
    options: {
        responsive: true,
        cutout: '70%',
        plugins: { legend: { display: false } },
        rotation: -90,
        circumference: 180
    }
});

// ---- Pie répartition des notes ----
const pieLabels = Object.keys(survey.pieCounts).map(k=>`Note ${k}`);
const pieValues = Object.values(survey.pieCounts);
new Chart(document.getElementById('pieChart'), {
    type: 'pie',
    data: { labels: pieLabels, datasets: [{ data: pieValues }]},
    options: { responsive: true }
});

// ---- Courbe temporelle ----
new Chart(document.getElementById('lineChart'), {
    type: 'line',
    data: { labels: survey.timeline.labels, datasets: [{ label: 'Moyenne', data: survey.timeline.values, tension: .3, fill: false }]},
    options: { scales: { y: { ...mkTicks(5) } } }
});

// ---- Bar: moyenne par catégorie (vertical) ----
new Chart(document.getElementById('barCatChart'), {
    type: 'bar',
    data: { labels: survey.categories.labels, datasets: [{ label: 'Moyenne', data: survey.categories.values }]},
    options: { scales: { y: { ...mkTicks(5) } }, plugins:{ legend:{ display:false } } }
});

// ---- Bar horizontale moyenne par question ----
new Chart(document.getElementById('barQuestionChart'), {
    type: 'bar',
    data: { labels: survey.byQuestion.labels, datasets: [{ label: 'Moyenne', data: survey.byQuestion.values }]},
    options: { indexAxis: 'y', scales: { x: { ...mkTicks(5) } }, plugins:{ legend:{ display:false } } }
});