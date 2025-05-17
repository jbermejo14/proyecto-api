<?php
require_once '../model/Usuario.php';

if ($_SERVER['REQUEST_METHOD'] === 'POST') {

    if (Usuario::registrar($_POST['nombre'], $_POST['email'], $_POST['password'])) {
        // Servidor
        mail($_POST['email'], "Registro exitoso", "Te has registrado correctamente");
        header("Location: ../login.php");
    } else {
        // Si el correo ya está registrado, redirigimos a la página de registro con un mensaje de error
        header("Location: ../register.php?error=2");
        exit;
    }
}

?>
