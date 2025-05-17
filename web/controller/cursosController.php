<?php
require_once '../model/Curso.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $accion = $_POST['accion'];

    if ($accion === 'añadir') {
        Curso::añadir($_POST['title'], $_POST['description'], $_POST['startDate'], $_POST['categoria_id']);
        header("Location: ../admin.php");
        exit;
    } elseif ($accion === 'editar') {
        Curso::editar($_POST['id'], $_POST['title'], $_POST['description'], $_POST['startDate'], $_POST['categoria_id']);
        header("Location: ../admin.php");
        exit;
    } elseif ($accion === 'eliminar') {
        Curso::eliminar($_POST['id']);
        header('Content-Type: application/json');
        echo json_encode(['success' => true]);
        exit;
    }
}
