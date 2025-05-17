<?php
session_start();     // Inicia la sesión
session_destroy();   // Destruye la sesión, eliminando todos los datos guardados en la sesión
?>
<?php include 'view/header.php'; ?>
<main>
    <div style="padding: 10px 20px; text-align: left;">
        <div style="font-weight: bold; color: black; font-size: 20px; margin-top: 20px; margin-bottom: 5px;">
            You have logged out.
        </div>
    </div>

    <script>
        setTimeout(function() {
            window.location.href = 'index.php';  // Redirige a la página de inicio
        }, 5000);  // 5000 milisegundos = 5 segundos
    </script>

    <div style="padding: 10px 20px; text-align: left;">
        <div style="font-weight: normal; color: black;">
            You have logged out and will be redirected to the homepage in 5 seconds.
        </div>
    </div>

</main>

