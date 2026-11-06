@echo off
chcp 65001 > nul
echo ===================================================================
echo     CONFIGURANDO CONTRASEÑA DE POSTGRESQL AUTOMÁTICAMENTE
echo ===================================================================

net session >nul 2>&1
if %errorLevel% neq 0 (
    echo.
    echo [ATENCIÓN] Necesitas ejecutar este archivo como Administrador.
    echo.
    echo 1. Cierra esta ventana.
    echo 2. Haz CLIC DERECHO sobre este archivo (cambiar_clave_postgres.bat).
    echo 3. Selecciona "Ejecutar como administrador".
    echo.
    pause
    exit /b 1
)

set CONF="C:\Program Files\PostgreSQL\17\data\pg_hba.conf"
echo [1/3] Habilitando acceso local en pg_hba.conf...
powershell -Command "(Get-Content %CONF%) -replace 'host\s+all\s+all\s+127\.0\.0\.1/32\s+scram-sha-256', 'host    all             all             127.0.0.1/32            trust' -replace 'host\s+all\s+all\s+::1/128\s+scram-sha-256', 'host    all             all             ::1/128                 trust' | Set-Content %CONF%"

echo [2/3] Reiniciando servicio PostgreSQL 17...
net stop postgresql-x64-17 >nul 2>&1
net start postgresql-x64-17

echo [3/3] Configurando contraseña 'admin123' y creando base de datos 'clima'...
"C:\Program Files\PostgreSQL\17\bin\psql.exe" -U postgres -h 127.0.0.1 -c "ALTER USER postgres WITH PASSWORD 'admin123';"
"C:\Program Files\PostgreSQL\17\bin\psql.exe" -U postgres -h 127.0.0.1 -c "CREATE DATABASE clima;" >nul 2>&1

echo.
echo ===================================================================
echo  ¡ÉXITO!
echo  Tu contraseña ahora es: admin123
echo  La base de datos 'clima' está lista.
echo ===================================================================
echo.
pause

