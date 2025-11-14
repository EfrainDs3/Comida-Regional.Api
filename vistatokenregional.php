<?php
$apiBaseUrl = $_ENV['REGIONAL_API_BASE_URL'] ?? 'http://localhost:8080';

// Evitar caché en el navegador para ver cambios inmediatamente
header('Cache-Control: no-cache, no-store, must-revalidate'); // HTTP 1.1.
header('Pragma: no-cache'); // HTTP 1.0.
header('Expires: 0'); // Proxies.

// Registrar registro de depuración y versión del archivo para confirmar qué copia se sirve
$filePath = __FILE__;
$version = date('Y-m-d H:i:s');
error_log('Base URL: ' . $apiBaseUrl);
error_log('Serving file: ' . $filePath . ' (version ' . $version . ')');

$tokenResult = null;
$errorMessage = null;

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $nombres = trim($_POST['nombres'] ?? '');
    $apellidos = trim($_POST['apellidos'] ?? '');
    $email = trim($_POST['email'] ?? '');

    if ($nombres === '' || $apellidos === '' || $email === '') {
        $errorMessage = 'Debes ingresar los nombres, apellidos y el email.';
    } else {
        $payload = json_encode([
            'nombres'   => $nombres,
            'apellidos' => $apellidos,
            'email'     => $email,
        ], JSON_UNESCAPED_UNICODE);

        $curl = curl_init(rtrim($apiBaseUrl, '/') . '/restful/token');
        curl_setopt_array($curl, [
            CURLOPT_POST           => true,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_HTTPHEADER     => [
                'Content-Type: application/json',
                'Accept: application/json',
            ],
            CURLOPT_POSTFIELDS     => $payload,
            CURLOPT_TIMEOUT        => 30, // Aumentar el tiempo de espera
        ]);

        $responseBody = curl_exec($curl);
        $httpCode     = curl_getinfo($curl, CURLINFO_HTTP_CODE);
        $curlError    = curl_error($curl);
        curl_close($curl);

        // Registrar errores para depuración
        if ($curlError) {
            error_log('Error cURL: ' . $curlError);
        }
        error_log('HTTP Code: ' . $httpCode);
        error_log('Response Body: ' . $responseBody);

        if ($curlError) {
            $errorMessage = 'No se pudo contactar al backend: ' . $curlError;
        } elseif ($httpCode >= 200 && $httpCode < 300) {
            $decoded = json_decode($responseBody, true);
            if (is_array($decoded) && isset($decoded['token'])) {
                $tokenResult = $decoded;
            } else {
                $errorMessage = 'Respuesta desconocida del servidor: ' . $responseBody;
            }
        } else {
            $decoded = json_decode($responseBody, true);
            if (is_array($decoded) && isset($decoded['message'])) {
                $errorMessage = $decoded['message'];
            } else {
                $errorMessage = 'No se pudo generar el token (HTTP ' . $httpCode . ').';
            }
        }
    }
}
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>API Comida Regional &mdash; Solicitud de Token</title>
    <style>
        :root {
            --bg-dark: #0f2c2c;
            --bg-panel: #1f4d4d;
            --accent: #f7931e;
            --text-light: #f6f6f6;
            --text-muted: #dbe7e7;
            --success: #2ecc71;
            --error: #e74c3c;
        }
        * { box-sizing: border-box; }
        body {
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: var(--bg-dark) url('https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?auto=format&fit=crop&w=1600&q=80') center/cover fixed;
            color: var(--text-light);
        }
        .overlay {
            min-height: 100vh;
            background: rgba(15, 44, 44, 0.88);
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding: 4rem 1rem;
        }
        .container {
            max-width: 960px;
            width: 100%;
            background: rgba(12, 31, 31, 0.92);
            border-radius: 18px;
            box-shadow: 0 20px 35px rgba(0, 0, 0, 0.55);
            overflow: hidden;
            border: 1px solid rgba(255, 255, 255, 0.08);
        }
        .hero {
            padding: 3rem;
            text-align: center;
            background: linear-gradient(135deg, rgba(31, 77, 77, 0.92), rgba(12, 31, 31, 0.92));
        }
        .hero h1 {
            margin: 0 0 0.5rem;
            font-size: 2.5rem;
            letter-spacing: 0.04em;
        }
        .hero h2 {
            margin: 0 0 1.5rem;
            font-weight: 400;
            letter-spacing: 0.08em;
        }
        .hero p {
            max-width: 720px;
            margin: 0 auto;
            color: var(--text-muted);
            line-height: 1.6;
        }
        .form-section {
            padding: 3rem;
            background: rgba(9, 24, 24, 0.94);
        }
        .section-title {
            font-size: 1.75rem;
            margin-bottom: 1rem;
            text-align: center;
        }
        .underline {
            width: 140px;
            height: 6px;
            margin: 0 auto 2.5rem;
            background: linear-gradient(90deg, #0e1f1f, #2ecc71, #0e1f1f);
            border-radius: 999px;
        }
        form {
            max-width: 520px;
            margin: 0 auto;
        }
        label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 600;
            letter-spacing: 0.03em;
        }
        input[type="text"],
        input[type="password"],
        input[type="email"] {
            width: 100%;
            padding: 0.85rem 1rem;
            border-radius: 12px;
            border: 1px solid rgba(255, 255, 255, 0.18);
            background: rgba(255, 255, 255, 0.08);
            color: var(--text-light);
            margin-bottom: 1.2rem;
            transition: border-color 0.2s ease, background 0.2s ease;
        }
        input[type="text"]:focus,
        input[type="password"]:focus,
        input[type="email"]:focus {
            outline: none;
            border-color: var(--accent);
            background: rgba(255, 255, 255, 0.15);
        }
        .actions {
            text-align: center;
        }
        button {
            background: var(--accent);
            color: #0c1f1f;
            border: none;
            border-radius: 12px;
            padding: 0.9rem 2.5rem;
            font-size: 1rem;
            font-weight: 700;
            cursor: pointer;
            transition: transform 0.15s ease, box-shadow 0.15s ease;
        }
        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 18px rgba(247, 147, 30, 0.25);
        }
        .flash {
            margin: 0 auto 1.5rem;
            max-width: 520px;
            padding: 1rem 1.25rem;
            border-radius: 12px;
            line-height: 1.5;
        }
        .flash.error {
            background: rgba(231, 76, 60, 0.18);
            border: 1px solid rgba(231, 76, 60, 0.4);
        }
        .flash.success {
            background: rgba(46, 204, 113, 0.18);
            border: 1px solid rgba(46, 204, 113, 0.4);
        }
        .token-box {
            margin: 2rem auto 0;
            max-width: 520px;
            padding: 1.5rem;
            border-radius: 16px;
            background: rgba(255, 255, 255, 0.06);
            border: 1px solid rgba(255, 255, 255, 0.08);
        }
        .token-box pre {
            white-space: pre-wrap;
            word-break: break-all;
            margin: 0;
            font-family: 'Fira Mono', 'Courier New', monospace;
            background: rgba(0, 0, 0, 0.35);
            padding: 1rem;
            border-radius: 12px;
        }
        .meta {
            margin-top: 1rem;
            font-size: 0.9rem;
            color: var(--text-muted);
        }
        .note {
            margin: 2.5rem auto 0;
            max-width: 520px;
            font-size: 0.92rem;
            color: var(--text-muted);
            line-height: 1.6;
        }
        @media (max-width: 768px) {
            .hero, .form-section {
                padding: 2.2rem 1.5rem;
            }
            .hero h1 {
                font-size: 2.1rem;
            }
        }
    </style>
</head>
<body>
<div class="overlay">
    <div class="container">
        <header class="hero">
            <h1>API Modelo de Negocio</h1>
            <h2>Desarrolla integraciones con nosotros</h2>
            <p>
                Usa tus <strong>nombres</strong>, <strong>apellidos</strong> y tu <strong>email</strong> para solicitar un token JWT.
                Este token se utiliza en la cabecera <code>Authorization</code> de todas las peticiones REST:
                <code>Authorization: Bearer &lt;token&gt;</code>.
            </p>
        </header>
        <section class="form-section">
            <h3 class="section-title">Solicitud de Acceso a Datos</h3>
            <div class="underline"></div>

            <?php if ($errorMessage): ?>
                <div class="flash error">⚠ <?php echo htmlspecialchars($errorMessage, ENT_QUOTES, 'UTF-8'); ?></div>
            <?php endif; ?>

            <?php if ($tokenResult): ?>
                <div class="flash success">✅ Token generado correctamente.</div>
                <div class="token-box">
                    <pre id="tokenValue"><?php echo htmlspecialchars($tokenResult['token'], ENT_QUOTES, 'UTF-8'); ?></pre>
                    <div class="meta">Expira en: <?php echo htmlspecialchars($tokenResult['expires_in'], ENT_QUOTES, 'UTF-8'); ?> segundos.</div>
                </div>
                <button onclick="copyToken()">Copiar Token</button>
            <?php endif; ?>

            <form method="post" autocomplete="off">
                <label for="nombres">Nombres</label>
                <input type="text" name="nombres" id="nombres" placeholder="Ingresa tus nombres" required>

                <label for="apellidos">Apellidos</label>
                <input type="text" name="apellidos" id="apellidos" placeholder="Ingresa tus apellidos" required>

                <label for="email">Email</label>
                <input type="email" name="email" id="email" placeholder="Ingresa tu email" required>

                <div class="actions">
                    <button type="submit">Solicitar token</button>
                </div>
            </form>

            <p class="note">
                Si aún no tienes credenciales, registra tus datos en nuestro equipo de soporte para recibir tu
                <strong>nombres</strong>, <strong>apellidos</strong> y el <strong>email</strong> inicial. Guarda la información en un lugar seguro;
                no se volverá a mostrar una vez restablecida.
            </p>
            <div class="meta" style="text-align:center; margin-top:1rem; font-size:0.85rem; color:var(--text-muted);">
                Última actualización: <?php echo htmlspecialchars($version, ENT_QUOTES, 'UTF-8'); ?> — Archivo: <?php echo htmlspecialchars(basename($filePath), ENT_QUOTES, 'UTF-8'); ?>
            </div>
        </section>
    </div>
</div>
<script>
function copyToken() {
    const tokenElement = document.getElementById('tokenValue');
    if (!tokenElement) {
        return;
    }
    const range = document.createRange();
    range.selectNode(tokenElement);
    const selection = window.getSelection();
    selection.removeAllRanges();
    selection.addRange(range);
    document.execCommand('copy');
    selection.removeAllRanges();
    alert('Token copiado al portapapeles.');
}
</script>
</body>
</html>