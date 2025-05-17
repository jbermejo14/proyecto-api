<?php
session_start();

if (!isset($_SESSION['usuario']) || $_SESSION['usuario']['rol'] !== 'admin') {
    header("Location: login.php");
    exit();
}

require_once 'model/db.php';

$conn = DB::connect();
$sql = "SELECT nombre, email, rol FROM usuarios";
$result = $conn->query($sql);
?>

<?php include 'view/header.php'; ?>

<style>
    .container {
        max-width: 900px;
        margin: 60px auto;
        padding: 0 30px;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    .page-title {
        text-align: center;
        margin-bottom: 50px;
        color: #333;
        font-weight: 700;
        font-size: 2.7rem;
    }

    .students-list {
        margin-bottom: 60px;
    }

    .styled-table {
        width: 100%;
        border-collapse: separate;
        border-spacing: 0 12px;
        font-size: 1rem;
        box-shadow: 0 0 18px rgba(0, 0, 0, 0.12);
        border-radius: 10px;
        overflow: hidden;
    }

    .styled-table thead tr {
        background-color: #4CAF50;
        color: #ffffff;
        text-align: left;
    }

    .styled-table th,
    .styled-table td {
        padding: 16px 20px;
    }

    .styled-table tbody tr {
        background-color: #fff;
        border-radius: 10px;
        box-shadow: 0 2px 6px rgba(0,0,0,0.08);
    }

    .role-tag {
        padding: 6px 14px;
        border-radius: 25px;
        color: #fff;
        font-weight: 600;
        font-size: 0.95rem;
        display: inline-block;
        text-transform: capitalize;
    }

    .role-admin {
        background-color: #f39c12; /* naranja para admin */
    }

    .role-estudiante {
        background-color: #27ae60; /* verde para student */
    }

    /* Puedes agregar otros roles si tienes */
</style>

<div class="container">
    <h1 class="page-title">Students Classroom</h1>

    <div class="students-list">
        <table class="styled-table">
            <thead>
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Rol</th>
            </tr>
            </thead>
            <tbody>
            <?php while ($user = $result->fetch_assoc()): ?>
                <tr>
                    <td><?= htmlspecialchars($user['nombre']) ?></td>
                    <td><?= htmlspecialchars($user['email']) ?></td>
                    <td>
                        <span class="role-tag role-<?= strtolower($user['rol']) ?>">
                            <?= htmlspecialchars(ucfirst($user['rol'])) ?>
                        </span>
                    </td>
                </tr>
            <?php endwhile; ?>
            </tbody>
        </table>
    </div>
</div>

<?php include 'view/footer.php'; ?>
