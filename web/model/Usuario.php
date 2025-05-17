<?php
require_once 'db.php';

class Usuario {


    public static function registrar($nombre, $email, $password) {
        $conn = DB::connect();

        // Verificar si el correo electrónico ya está registrado
        $stmt = $conn->prepare("SELECT * FROM usuarios WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $res = $stmt->get_result();

        // Si el correo ya existe, retorna false
        if ($res->num_rows > 0) {
            return false;
        }
        // Si el correo no existe, proceder con el registro
        $stmt = $conn->prepare("INSERT INTO usuarios (nombre, email, password) VALUES (?, ?, ?)");
        $hash = password_hash($password, PASSWORD_DEFAULT);
        $stmt->bind_param("sss", $nombre, $email, $hash);
        return $stmt->execute();
    }

    public static function login($email, $password) {
        $conn = DB::connect();
        $stmt = $conn->prepare("SELECT * FROM usuarios WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $res = $stmt->get_result();
        if ($res->num_rows === 1) {
            $usuario = $res->fetch_assoc();
            if (password_verify($password, $usuario['password'])) {
                return $usuario;
            }
        }
        return false;
    }
}
?>
