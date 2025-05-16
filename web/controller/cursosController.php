<?php
require_once '../model/Curso.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $accion = $_POST['accion'];

    if ($accion === 'añadir') {
        Curso::añadir($_POST['title'], $_POST['description'], $_POST['startDate'], $_POST['categoria_id']);
    } elseif ($accion === 'editar') {
        Curso::editar($_POST['id'], $_POST['title'], $_POST['description'], $_POST['startDate'], $_POST['categoria_id']);
    } elseif ($accion === 'eliminar') {
        Curso::eliminar($_POST['id']);
    }

    header("Location: ../admin.php");
    exit;
}