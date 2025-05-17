-- Usuarios
CREATE TABLE usuarios (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          email VARCHAR(100) UNIQUE NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          rol ENUM('admin', 'student') DEFAULT 'student'
);
CREATE TABLE categorias (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            nombre VARCHAR(100) NOT NULL
);
-- Cursos
CREATE TABLE cursos (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        description TEXT,
                        startDate DATE,
                        active BOOLEAN DEFAULT TRUE,
                        categoria_id INT,
                        FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE SET NULL
);

INSERT INTO categorias (nombre) VALUES ('Programacion'), ('Redes'), ('Otros');
