<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Zuul Project - Pokémon Delta Emerald</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            line-height: 1.6;
            color: #2c3e50;
            max-width: 900px;
            margin: 0 auto;
            padding: 20px;
            background-color: #e8f5e9; /* Light Emerald Green */
        }
        header {
            background-color: #1b5e20; /* Dark Green */
            color: #ffffff;
            padding: 30px;
            text-align: center;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.2);
            margin-bottom: 30px;
        }
        .container {
            background: #ffffff;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        h2 {
            color: #2e7d32;
            border-bottom: 3px solid #81c784;
            padding-bottom: 10px;
            margin-top: 40px;
        }
        h3 { color: #388e3c; }
        ul { list-style-type: disc; margin-left: 20px; }
        .map-section {
            background-color: #f1f8e9;
            border: 2px solid #a5d6a7;
            padding: 20px;
            margin-top: 20px;
            font-family: monospace;
            white-space: pre;
            overflow-x: auto;
        }
        footer {
            text-align: center;
            margin-top: 50px;
            font-size: 0.9em;
            color: #666;
            border-top: 1px solid #ccc;
            padding-top: 20px;
        }
        strong { color: #1b5e20; }
    </style>
</head>
<body>

<header>
    <h1>Zuul Adventure: Pokémon Delta Emerald</h1>
    <p>Provisional Title: A3P 2025/2026 g&alpha;</p>
</header>

<div class="container">
    
    <h2>I.A) Author</h2>
    <p><strong>Name:</strong> [Your Name Here]</p>

    <h2>I.B) Theme (Exercise 7.1.1)</h2>
    <p>
        The game is based on the <strong>Hoenn Region</strong> from Pokémon Emerald. 
        Unlike the original turn-based RPG, this is a text-based adventure where the player 
        must navigate through routes and cities, solve environmental puzzles, and stop 
        the climate crisis caused by legendary Pokémon.
    </p>

    <h2>I.C) Complete Scenario Summary (Exercise 7.3.1)</h2>
    <p>
        The player starts in <strong>Littleroot Town</strong>. After obtaining a starter Pokémon 
        from Professor Birch, they realize that the weather in Hoenn is becoming dangerously 
        unstable. Heavy rains and extreme droughts are clashing.
    </p>
    <p>
        To save the region, the player must travel to <strong>Rustboro City</strong> to find 
        the Devon Parts, navigate the <strong>Petalburg Woods</strong>, and ultimately reach 
        the <strong>Sky Pillar</strong>. The goal is to find the "Emerald Orb" and use it to 
        summon Rayquaza to stop the fighting between Groudon and Kyogre. 
        <strong>Win Condition:</strong> Reach the Sky Pillar with the Emerald Orb.
    </p>

    <h2>I.D) Full Plan (Exercise 7.3.2)</h2>
    <p>The Hoenn map structure for the Zuul engine:</p>
    
    <div class="map-section">
[ Sky Pillar ]
      ^
      | (North)
[ Rustboro City ] <--- [ Petalburg Woods ]
                              ^
                              | (North)
[ Oldale Town ] ----> [ Route 102 ] ----> [ Petalburg City ]
      ^
      | (North)
[ Route 101 ]
      ^
      | (North)
[ Littleroot Town ] ---->  [ Home ]
    </div>

    <ul>
	<li><strong>The House:</strong> The starting point. A safe place to rest before your journey.</li>
        <li><strong>Littleroot Town:</strong> Starting room (Home).</li>
        <li><strong>Route 101:</strong> Connection between Littleroot and Oldale.</li>
        <li><strong>Oldale Town:</strong> Junction point.</li>
        <li><strong>Route 102:</strong> Path to the west.</li>
        <li><strong>Petalburg City:</strong> Major city (Gym location).</li>
        <li><strong>Petalburg Woods:</strong> A maze-like forest room.</li>
        <li><strong>Rustboro City:</strong> The industrial center.</li>
        <li><strong>Sky Pillar:</strong> The final destination (Victory Room).</li>
    </ul>

</div>

<footer>
    <p>Project realized for E1/A3P Unit - ESIEE Paris</p>
</footer>

</body>
</html>
