<?php
session_start();
if (!isset($_SESSION['usuario']) || !in_array($_SESSION['usuario']['rol'], ['student', 'admin'])) {
    header("Location: index.php");
    exit;
}

include 'header.php';
require_once 'model/Curso.php';

$categoria_id = isset($_GET['categoria']) ? (int)$_GET['categoria'] : null;
$cursos = Curso::listarPorCategoria($categoria_id);
?>

    <style>
        /* Filtros estilo Google */
        .filtros-categorias {
            text-align: center;
            margin: 40px auto;
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 12px;
        }

        .filtros-categorias a {
            background-color: white;
            color: #1a73e8;
            padding: 10px 25px;
            border: 2px solid #1a73e8;
            border-radius: 24px;
            font-weight: 500;
            text-decoration: none;
            transition: all 0.3s ease;
        }

        .filtros-categorias a:hover {
            background-color: #1a73e8;
            color: white;
        }

        .filtros-categorias a.activo {
            background-color: #1a73e8;
            color: white;
            border-color: #1a73e8;
        }

        /* Contenedor general */
        main {
            max-width: 1200px;
            margin: 0 auto 60px auto;
            padding: 0 20px;
        }

        /* Tarjetas estilo Material Design */
        .curso {
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.12);
            padding: 20px;
            margin: 20px;
            width: 280px;
            display: inline-block;
            vertical-align: top;
            transition: all 0.3s ease;
            border-top: 4px solid #1a73e8;
        }

        .curso:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
            border-color: #185abc;
        }

        .curso h3 {
            font-size: 18px;
            color: #202124;
            margin-bottom: 10px;
        }

        .curso p {
            font-size: 14px;
            color: #5f6368;
            margin-bottom: 8px;
        }

        .curso p strong {
            color: #202124;
        }
        .estado-curso {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .estado-curso span {
            display: inline-block;
            width: 10px;
            height: 10px;
            border-radius: 50%;
            margin-right: 8px;
        }

        .estado-activo {
            background-color: #34a853; /* verde */
        }

        .estado-inactivo {
            background-color: #ea4335; /* rojo */
        }

        .estado-texto {
            font-size: 13px;
            color: #5f6368;
        }

    </style>


    <main>
        <div class="filtros-categorias">
            <a href="?categoria=1" class="<?= (isset($_GET['categoria']) && $_GET['categoria'] == 1) ? 'activo' : '' ?>">Programming</a>
            <a href="?categoria=2" class="<?= (isset($_GET['categoria']) && $_GET['categoria'] == 2) ? 'activo' : '' ?>">Networks</a>
            <a href="?categoria=3" class="<?= (isset($_GET['categoria']) && $_GET['categoria'] == 3) ? 'activo' : '' ?>">Others</a>
        </div>

        <div>
            <?php while ($c = $cursos->fetch_assoc()): ?>
                <div class="curso">
                    <div class="estado-curso">
                        <span class="<?= $c['active'] ? 'estado-activo' : 'estado-inactivo' ?>"></span>
                        <span class="estado-texto"><?= $c['active'] ? 'Active' : 'Inactive' ?></span>
                    </div>
                    <h3><?= htmlspecialchars($c['title']) ?></h3>
                    <p><?= htmlspecialchars($c['description']) ?></p>
                    <p><strong>Start date:</strong> <?= $c['startDate'] ?></p>
                </div>
            <?php endwhile; ?>
        </div>

    </main>

<?php include 'footer.php'; ?>