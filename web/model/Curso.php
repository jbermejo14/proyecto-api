<?php
require_once 'db.php';

class Curso {
    public static function listarPorCategoria($categoria_id = null) {
        $conn = DB::connect();
        if ($categoria_id) {
            $stmt = $conn->prepare("SELECT * FROM cursos WHERE categoria_id = ? AND active = 1");
            $stmt->bind_param("i", $categoria_id);
        } else {
            $stmt = $conn->prepare("SELECT * FROM cursos WHERE active = 1");
        }
        $stmt->execute();
        return $stmt->get_result();
    }

    public static function añadir($title, $description, $startDate, $categoria_id) {
        $conn = DB::connect();
        $stmt = $conn->prepare("INSERT INTO cursos (title, description, startDate, categoria_id) VALUES (?, ?, ?, ?)");
        $stmt->bind_param("sssi", $title, $description, $startDate, $categoria_id);
        return $stmt->execute();
    }

    public static function editar($id, $title, $description, $startDate, $categoria_id) {
        $conn = DB::connect();
        $stmt = $conn->prepare("UPDATE cursos SET title=?, description=?, startDate=?, categoria_id=? WHERE id=?");
        $stmt->bind_param("sssii", $title, $description, $startDate, $categoria_id, $id);
        return $stmt->execute();
    }

    public static function eliminar($id) {
        $conn = DB::connect();
        $stmt = $conn->prepare("DELETE FROM cursos WHERE id = ?");
        $stmt->bind_param("i", $id);
        return $stmt->execute();
    }
}