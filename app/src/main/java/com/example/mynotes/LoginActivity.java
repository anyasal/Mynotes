package com.example.mynotes;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mynotes.Database.AppDatabase;
import com.example.mynotes.Database.MainDAO;
import com.example.mynotes.Models.User;

public class LoginActivity extends AppCompatActivity {
    private EditText etLogin, etPassword;
    private Button btnLogin, btnRegister;
    private MainDAO mainDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Инициализация элементов интерфейса
        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        // Инициализация базы данных
        AppDatabase db = AppDatabase.getInstance(this);
        mainDAO = db.mainDAO();
        btnLogin.setOnClickListener(v -> {
            String login = etLogin.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            // Проверка пользователя в базе данных
            User user = mainDAO.getUserByLogin(login);
            if (user == null) {
                Toast.makeText(this, "Пользователь не найден", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!user.getPassword().equals(password)) {
                Toast.makeText(this, "Неверный пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            // Успешная авторизация
            Toast.makeText(this, "Авторизация успешна", Toast.LENGTH_SHORT).show();

            // Сохраняем флаг авторизации
            SharedPreferences prefs = getSharedPreferences("auth", MODE_PRIVATE);
            prefs.edit().putBoolean("isLoggedIn", true).apply();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class); // Переход на главную активность
            startActivity(intent);
            finish(); // Закрываем текущую активность
        });
        // Обработка нажатия на кнопку "Зарегистрироваться"
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}