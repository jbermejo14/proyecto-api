<?php
include 'view/header.php';
require_once 'model/Curso.php';

if (!isset($_SESSION['usuario']) || $_SESSION['usuario']['rol'] !== 'admin') {
    echo "Acceso denegado";
    exit;
}

$cursos = Curso::listarPorCategoria();
?>

    <style>
        tr.inactivo {
            background-color: #f8d7da;
            opacity: 0.7;
        }

    </style>

    <main>
        <h2>Course Administration Panel</h2>

        <h3>Add Course</h3>
        <form action="controller/cursosController.php" method="POST">
            <input type="hidden" name="accion" value="añadir">
            Title: <input type="text" name="title" required><br>
            Description: <textarea name="description" required></textarea><br>
            Start Date: <input type="date" name="startDate" required><br>
            Category:
            <select name="categoria_id" required>
                <option value="1">Programming</option>
                <option value="2">Networks</option>
                <option value="3">Others</option>
            </select><br>
            Active: <input type="checkbox" name="active" checked><br>
            <input type="submit" value="Add course">
        </form>

        <h3>Existing courses</h3>
        <table border="1" cellpadding="5">
            <tr><th>Title</th><th>Description</th><th>Start date</th><th>Category</th><th>Active</th><th>Actions</th></tr>
            <?php while ($c = $cursos->fetch_assoc()): ?>
                <tr class="<?= !$c['active'] ? 'inactivo' : '' ?>">
                    <form action="controller/cursosController.php" method="POST">
                        <input type="hidden" name="id" value="<?= $c['id'] ?>">
                        <input type="hidden" name="accion" value="editar">
                        <td><input type="text" name="title" value="<?= htmlspecialchars($c['title']) ?>"></td>
                        <td><textarea name="description"><?= htmlspecialchars($c['description']) ?></textarea></td>
                        <td><input type="date" name="startDate" value="<?= $c['startDate'] ?>"></td>
                        <td>
                            <select name="categoria_id" required>
                                <option value="1" <?= $c['categoria_id'] == 1 ? 'selected' : '' ?>>Programming</option>
                                <option value="2" <?= $c['categoria_id'] == 2 ? 'selected' : '' ?>>Networks</option>
                                <option value="3" <?= $c['categoria_id'] == 3 ? 'selected' : '' ?>>Others</option>
                            </select>
                        </td>
                        <td><input type="checkbox" name="active" <?= $c['active'] ? 'checked' : '' ?>></td>
                        <td>
                            <input type="submit" value="Save">
                    </form>
                    <form action="controller/cursosController.php" method="POST" style="display:inline;">
                        <input type="hidden" name="accion" value="eliminar">
                        <input type="hidden" name="id" value="<?= $c['id'] ?>">
                        <input type="submit" value="Delete" onclick="return confirm('Delete this course?');">
                    </form>
                    </td>
                </tr>
            <?php endwhile; ?>
        </table>
    </main>

<?php include 'view/footer.php'; ?>