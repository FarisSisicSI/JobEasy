package templates;

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>JobEasy - Registracija</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/bootstrap.min.css}"/>
    <link rel="stylesheet" type="text/css" th:href="@{/css/bootstrap.css}"/>
    <link rel="stylesheet" type="text/css" th:href="@{/css/customDugme.css}" />
    <style>
        .form-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 12px;
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.2);
        }

        .form-container h1 {
            font-size: 1.8rem;
            margin-bottom: 20px;
            text-align: center;
            font-weight: bold;
            color: #343a40;
        }

        .form-container label {
            font-weight: bold;
            color: #495057;
        }

        .form-container .form-control {
            border-radius: 8px;
            border: 1px solid #ced4da;
            padding: 10px;
            transition: box-shadow 0.2s ease-in-out;
        }

        .form-container .form-control:focus {
            box-shadow: 0 0 8px rgba(0, 123, 255, 0.25);
            border-color: #80bdff;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-dark" data-bs-theme="dark">
    <div class="container-fluid">
        <a class="navbar-brand" th:href="@{/welcome}">Početna</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <button class="Btn">
                        <div class="sign"><svg viewBox="0 0 512 512"><path d="M377.9 105.9L500.7 228.7c7.2 7.2 11.3 17.1 11.3 27.3s-4.1 20.1-11.3 27.3L377.9 406.1c-6.4 6.4-15 9.9-24 9.9c-18.7 0-33.9-15.2-33.9-33.9l0-62.1-128 0c-17.7 0-32-14.3-32-32l0-64c0-17.7 14.3-32 32-32l128 0 0-62.1c0-18.7 15.2-33.9 33.9-33.9c9 0 17.6 3.6 24 9.9zM160 96L96 96c-17.7 0-32 14.3-32 32l0 256c0 17.7 14.3 32 32 32l64 0c17.7 0 32 14.3 32 32s-14.3 32-32 32l-64 0c-53 0-96-43-96-96L0 128C0 75 43 32 96 32l64 0c17.7 0 32 14.3 32 32s-14.3 32-32 32z"></path></svg></div>
                        <div class="text"><a class="nav-link" style="color:white;"  th:href="@{/logout}">Odjava</a></div>
                    </button>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="form-container">
    <h1>Registracija korisnika</h1>
    <form th:action="@{/registracija}" th:object="${korisnik}" method="post">
        <div class="form-group mb-3">
            <label for="ime">Ime:</label>
            <input type="text" th:field="*{ime}" id="ime" class="form-control" placeholder="Unesite ime" required/>
        </div>
        
        <div class="form-group mb-3">
            <label for="prezime">Prezime:</label>
            <input type="text" th:field="*{prezime}" id="prezime" class="form-control" placeholder="Unesite prezime" required/>
        </div>

        <div class="form-group mb-3">
            <label for="email">Email:</label>
            <input type="email" th:field="*{email}" id="email" class="form-control" placeholder="Unesite email" required/>
        </div>

        <div class="form-group mb-3">
            <label for="username">Korisničko ime:</label>
            <input type="text" th:field="*{username}" id="username" class="form-control" placeholder="Unesite korisničko ime" required/>
        </div>

        <div class="form-group mb-3">
            <label for="password">Lozinka:</label>
            <input type="password" th:field="*{password}" id="password" class="form-control" placeholder="Unesite lozinku" required/>
        </div>

        <div class="text-center">
            <button class="btn btn-outline-success" type="submit">Registruj se</button>
            <a th:href="@{/welcome}" class="btn btn-outline-secondary">Nazad</a>
        </div>
    </form>
</div>
</body>
</html>
