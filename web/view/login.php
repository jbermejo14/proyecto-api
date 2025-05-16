<?php include 'header.php'; ?>

<style>
    body {
        background: #f1f3f4;
        font-family: 'Roboto', Arial, sans-serif;
        margin: 0;
        padding: 0;
    }

    .login-container {
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 80vh;
    }

    .login-card {
        background: #fff;
        padding: 40px 50px;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        width: 100%;
        max-width: 400px;
        text-align: center;
    }

    .login-card h2 {
        margin-bottom: 20px;
        font-weight: 400;
        color: #202124;
    }

    .login-card input[type="email"],
    .login-card input[type="password"] {
        width: 100%;
        padding: 12px;
        margin: 10px 0;
        border: 1px solid #dadce0;
        border-radius: 4px;
        font-size: 14px;
        background: #fff;
    }

    .login-card input[type="submit"] {
        width: 100%;
        padding: 12px;
        background-color: #1a73e8;
        color: white;
        border: none;
        border-radius: 4px;
        font-size: 14px;
        margin-top: 20px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }

    .login-card input[type="submit"]:hover {
        background-color: #155ab6;
    }

    .login-card .register-link {
        margin-top: 25px;
        display: block;
        font-size: 14px;
        color: #1a73e8;
        text-decoration: none;
    }

    .login-card .register-link:hover {
        text-decoration: underline;
    }

    .error-message {
        background-color: #fce8e6;
        color: #d93025;
        padding: 10px 15px;
        border-radius: 4px;
        margin-bottom: 15px;
        font-size: 14px;
    }
</style>

<main>
    <div class="login-container">
        <div class="login-card">
            <h2>Log In</h2>

            <?php if (isset($_GET['error']) && $_GET['error'] == 1): ?>
                <div class="error-message">
                    ⚠️ Incorrect username or password.
                </div>
            <?php endif; ?>

            <form action="controller/loginController.php" method="POST">
                <input type="email" name="email" placeholder="Email address" required>
                <input type="password" name="password" placeholder="Password" required>
                <input type="submit" value="Log In">
            </form>

            <a class="register-link" href="register.php">Don’t have an account? Sign up</a>
        </div>
    </div>
</main>

