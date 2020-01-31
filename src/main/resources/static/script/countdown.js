// On définie la date pour faire nos calculs
var endTime = new Date("Mar 20, 2020 09:00:00").getTime();

// On met à jour le calcul toutes les secondes
var x = setInterval(function () {

    // On cherche la date et heure actuelle
    var now = new Date().getTime();

    // On cherche la distance entre la date d'aujourd'hui et la date de calcul
    var distance = endTime - now;

    // On calcul les jours, heures, minutes, et secondes pour le compte à rebours
    var days = Math.floor(distance / (1000 * 60 * 60 * 24));
    var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = Math.floor((distance % (1000 * 60)) / 1000);

    // On change l'affichage si valeur < 10
    if (hours < "10") {
        hours = "0" + hours;
    }
    if (minutes < "10") {
        minutes = "0" + minutes;
    }
    if (seconds < "10") {
        seconds = "0" + seconds;
    }

    // On affiche les résultats dans les element associés
    document.getElementById("days").innerHTML = days + "<span>Jour(s)</span>";
    document.getElementById("hours").innerHTML = hours + "<span>Heure(s)</span>";
    document.getElementById("minutes").innerHTML = minutes + "<span>Minute(s)</span>";
    document.getElementById("seconds").innerHTML = seconds + "<span>Seconde(s)</span>";

    // Si le compte à rebours est fini, on affiche du texte et on cache le timer
    if (distance < 0) {
        clearInterval(x);
        document.getElementById("text").innerHtml = "IT'S OUT! GO GO GO~!";
        document.getElementById("days").innerHtml = "";
        document.getElementById("hours").innerHtml = "";
        document.getElementById("minutes").innerHtml = "";
        document.getElementById("seconds").innerHtml = "";
    }
}, 1000);
