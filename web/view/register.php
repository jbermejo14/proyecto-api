<?php include 'header.php'; ?>

<style>
    body {
        background: #f4f4f4;
        font-family: 'Roboto', Arial, sans-serif;
        margin: 0;
        padding: 0;
    }

    .form-container {
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        margin-top: 70px;
        margin-bottom: 100px;
        text-align: center;
    }

    form {
        width: 100%;
        max-width: 380px;
        background-color: white;
        padding: 40px;
        box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        border-radius: 8px;
    }

    form h2 {
        margin-bottom: 1em;
        font-weight: 500;
        font-size: 1.5em;
        color: #202124;
    }

    form input[type="text"],
    form input[type="email"],
    form input[type="password"] {
        width: 100%;
        padding: 0.9em;
        margin: 0.5em 0;
        border: 1px solid #dadce0;
        border-radius: 4px;
        background-color: #f8f9fa;
        font-size: 14px;
        transition: border-color 0.3s;
    }

    form input[type="text"]:focus,
    form input[type="email"]:focus,
    form input[type="password"]:focus {
        outline: none;
        border-color: #1a73e8;
        background-color: white;
    }

    form input[type="submit"] {
        width: 100%;
        padding: 0.9em;
        background-color: #1a73e8;
        color: white;
        font-weight: 500;
        border: none;
        border-radius: 4px;
        font-size: 14px;
        cursor: pointer;
        margin-top: 1.5em;
        transition: background-color 0.3s ease;
    }

    form input[type="submit"]:hover {
        background-color: #185abc;
    }

    .error-message {
        background-color: #fce8e6;
        padding: 12px 20px;
        margin-bottom: 20px;
        color: #d93025;
        border-radius: 4px;
        text-align: center;
        width: 100%;
        font-size: 14px;
    }
</style>

<main>
    <div class="form-container">

        <?php if (isset($_GET['error']) && $_GET['error'] == 2): ?>
            <div class="error-message">
                ⚠️ Este correo ya tiene una cuenta activa.
            </div>
        <?php endif; ?>

        <form action="controller/registerController.php" method="POST">
            <h2>Create account</h2>
            <input type="text" name="nombre" placeholder="Full name" required>
            <input type="email" name="email" placeholder="Email address" required>
            <input type="password" name="password" placeholder="Password" required>
            <input type="submit" value="Create an account">
        </form>
    </div>
</main>

