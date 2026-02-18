<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pokémon Delta Emerald - Projet Zuul</title>
    <style>
        /* Base reset and layout */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            line-height: 1.6;
            color: #333;
            background-color: #f4f7f6;
            margin: 0;
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        /* The wrapper ensures the green box and white box are the same width */
        .main-wrapper {
            width: 100%;
            max-width: 900px;
        }

        header {
            background-color: #2e7d32;
            color: white;
            padding: 40px 20px;
            text-align: center;
            border-radius: 8px 8px 0 0; /* Rounded only at the top */
            box-sizing: border-box; /* Crucial for alignment */
        }

        header h1 { 
            margin: 0; 
            font-size: 2.5rem;
        }

        header p { 
            margin: 10px 0 0;
            opacity: 0.9;
        }

        .container {
            background: white;
            padding: 30px;
            border-radius: 0 0 8px 8px; /* Rounded only at the bottom */
            box-shadow: 0 4px 15px rgba(0,0,0,0.05);
            box-sizing: border-box; /* Crucial for alignment */
            border-top: 1px solid rgba(0,0,0,0.05);
        }

        h2 {
            color: #2e7d32;
            border-bottom: 2px solid #a5d6a7;
            padding-bottom: 10px;
            margin-top: 30px;
        }

        .plan-img {
            max-width: 100%;
            height: auto;
            display: block;
            margin: 20px auto;
            border-radius: 4px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        footer {
            text-align: center;
            margin-top: 40px;
            font-size: 0.9em;
            color: #666;
        }
    </style>
</head>
<body>

    <div class="main-wrapper">
        <header>
            <h1>Pokémon Delta Emerald</h1>
            <p>Projet Zuul - ESIEE Paris</p>
        </header>

        <div class="container">
            <section>
                <h2>I.A) Auteur</h2>
                <p><strong>Barnabé Jouanard</strong></p>
            </section>

            <section>
                <h2>I.B) Thème</h2>
                <p>Ce projet est un jeu d'aventure textuel se déroulant dans la région de <strong>Hoenn</strong>. Il se concentre sur l'exploration environnementale pour stopper un conflit légendaire entre la terre et la mer.</p>
            </section>

            <section>
                <h2>I.C) Résumé du scénario</h2>
                <p>Le joueur incarne un dresseur débutant son voyage à <strong>Bourg-en-Vol</strong>. L'objectif est de retrouver l'<strong>Orbe Émeraude</strong> pour réveiller <strong>Rayquaza</strong> au sommet du <strong>Pilier Céleste</strong> et restaurer l'équilibre climatique de la région.</p>
            </section>

            <section>
                <h2>I.D) Plan complet</h2>
                <p>Organisation géographique de l'aventure :</p>
                <img src="https://github.com/Clascheurduturfu/Barnabe-Jouanard/blob/main/Dessin%20sans%20titre.png?raw=true" alt="Plan Hoenn" class="plan-img">
            </section>
        </div>
    </div>

    <footer>
        <p>&copy; 2026 Barnabé Jouanard - POO</p>
    </footer>

</body>
</html>
