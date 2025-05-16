<?php
class DB {
    private static $host = 'localhost';
    private static $db = 'online_courses';
    private static $user = 'root';
    private static $pass = '';
    public static function connect() {
        $conn = new mysqli(self::$host, self::$user, self::$pass, self::$db);
        if ($conn->connect_error) {
            die('Error de conexión: ' . $conn->connect_error);
        }
        return $conn;
    }
}
?>
