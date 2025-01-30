package com.example.mynotes;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mynotes.Database.AppDatabase;
import com.example.mynotes.Database.MainDAO;
import com.example.mynotes.Models.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNewLogin, etNewPassword;
    private Button btnRegister;
    private MainDAO mainDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Инициализация элементов интерфейса
        etNewLogin = findViewById(R.id.etNewLogin);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // Инициализация базы данных
        AppDatabase db = AppDatabase.getInstance(this);
        mainDAO = db.mainDAO();

        // Обработка нажатия на кнопку "Зарегистрироваться"
        btnRegister.setOnClickListener(v -> {
            String login = etNewLogin.getText().toString().trim();
            String password = etNewPassword.getText().toString().trim();

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            // Проверка, существует ли пользователь с таким логином
            if (mainDAO.checkLoginExists(login) > 0) {
                Toast.makeText(this, "Логин уже занят", Toast.LENGTH_SHORT).show();
                return;
            }

            // Создание нового пользователя
            User newUser = new User();
            newUser.setLogin(login);
            newUser.setPassword(password);
            mainDAO.insertUser(newUser);

            Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
            finish(); // Закрыть текущую активность и вернуться к авторизации
        });
    }
}