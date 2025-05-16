<?php
require_once '../model/Usuario.php';
session_start();

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $usuario = Usuario::login($_POST['email'], $_POST['password']);
    if ($usuario) {
        $_SESSION['usuario'] = $usuario;
        header("Location: ../cursos.php");
    } else {
        header("Location: ../login.php?error=1");
        exit;
    }
}
?>


?>
