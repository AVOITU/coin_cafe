document.getElementById('year').textContent = new Date().getFullYear();

// Soumission (démo) empêche l'envoi réel et affiche un récapitulatif JSON
document.getElementById('surveyForm').addEventListener('submit', function (e) {
    e.preventDefault();

    // Validation simple côté client (toutes les questions notées)
    const names = ['q1','q2','q3','q4','q5','q6','q7','q8','q9','q10'];
    for (const n of names) {
        if (!document.querySelector(`input[name="${n}"]:checked`)) {
            showAlert('Veuillez répondre à toutes les questions obligatoires.', 'danger');
            return;
        }
    }

    const data = Object.fromEntries(new FormData(this).entries());
    console.log('Réponses du sondage', data);

    showAlert('Merci ! Vos réponses ont été prises en compte. (Démo : rien n\'est envoyé, voir la console navigateur pour le JSON)', 'success');
    this.reset();
});

function showAlert(message, type) {
    const el = document.getElementById('formAlert');
    el.className = `alert alert-${type}`;
    el.textContent = message;
    el.classList.remove('d-none');
    window.scrollTo({ top: 0, behavior: 'smooth' });
}