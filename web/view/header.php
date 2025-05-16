<?php
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Online Courses</title>
    <style>
        /* Global Reset */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        html, body {
            font-family: 'Roboto', sans-serif;
            background-color: #f1f3f4;
            color: #202124;
            height: 100%;
        }

        body {
            display: flex;
            flex-direction: column;
        }

        /* Banner tipo Classroom */
        .envio-banner {
            background-color: #1a73e8;
            color: white;
            text-align: center;
            padding: 10px 0;
            font-size: 15px;
            font-weight: 500;
            letter-spacing: 0.5px;
        }

        /* Header tipo Google */
        header {
            background-color: #fff;
            border-bottom: 1px solid #dadce0;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 12px 30px;
            position: sticky;
            top: 0;
            z-index: 99;
        }

        .logo img {
            height: 40px;
        }

        .user-links {
            display: flex;
            align-items: center;
            font-weight: 500;
        }

        .user-links span {
            margin-right: 15px;
            color: #333;
        }

        .user-links a {
            text-decoration: none;
            margin-left: 15px;
            color: #1a73e8;
            transition: color 0.3s;
        }

        .user-links a:hover {
            text-decoration: underline;
            color: #0b53c1;
        }

        /* Contenedor de cursos estilo tarjeta */
        .cursos-container {
            max-width: 1200px;
            margin: 40px auto;
            padding: 0 20px;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
        }

        .curso-card {
            background-color: #ffffff;
            border-left: 6px solid #1a73e8;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            transition: transform 0.2s;
        }

        .curso-card:hover {
            transform: translateY(-4px);
        }

        .curso-card h3 {
            margin-bottom: 10px;
            font-size: 18px;
            color: #202124;
        }

        .curso-card p {
            font-size: 14px;
            color: #5f6368;
        }
    </style>
</head>
<body>

<!-- 🔵 Banner superior llamativo -->
<div class="envio-banner">
    🚀 New cybersecurity courses available starting from 2025-05-17
</div>

<!-- 🔹 Encabezado tipo Google -->
<header>
    <div class="logo">
        <a href="cursos.php">
            <img src="img/logo.png" alt="Logo">
        </a>
    </div>

    <div class="user-links">
        <?php if (isset($_SESSION['usuario'])): ?>
            Welcome, <?= htmlspecialchars($_SESSION['usuario']['nombre']) ?> |
            <a href="logout.php">Log out</a>
            <?php if ($_SESSION['usuario']['rol'] == 'admin'): ?>
                <a href="admin.php">Admin</a>
                <a href="students.php">Students</a>  <!-- botón para students solo admin -->
            <?php endif; ?>
        <?php endif; ?>
    </div>


</header>
