import 'dart:convert';
import 'package:http/http.dart' as http;

class ApiService {
  // ⚠️ Проверь — это IP твоего компьютера в локальной сети
  // Узнать можно через cmd → ipconfig → IPv4-адрес
  final String baseUrl = "http://192.168.0.101:8080/api";

  // Получение списка пользователей (если хочешь тестировать)
  Future<List<dynamic>> getUsers() async {
    final response = await http.get(Uri.parse('$baseUrl/users'));
    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Ошибка загрузки пользователей');
    }
  }

  // ✅ Регистрация пользователя
  Future<void> registerUser(
    String name,
    String phone,
    String iin,
    String password,
  ) async {
    final response = await http.post(
      Uri.parse('$baseUrl/register'), // ✅ правильный endpoint
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        'name': name,
        'phone': phone,
        'iin': iin,
        'password': password,
      }),
    );

    // ignore: avoid_print
    print("➡️ Отправлен запрос: $baseUrl/register");
    // ignore: avoid_print
    print(
      "📦 Тело: ${jsonEncode({'name': name, 'phone': phone, 'iin': iin, 'password': password})}",
    );
    // ignore: avoid_print
    print("📡 Ответ: ${response.statusCode} → ${response.body}");

    if (response.statusCode != 200 && response.statusCode != 201) {
      throw Exception('Ошибка регистрации: ${response.body}');
    }
  }

  // ✅ Вход пользователя
  Future<Map<String, dynamic>> loginUser(String phone, String password) async {
    final response = await http.post(
      Uri.parse('$baseUrl/login'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({'phone': phone, 'password': password}),
    );

    print("➡️ POST /login → ${response.statusCode}");
    print("Ответ: ${response.body}");

    if (response.statusCode == 200) {
      return jsonDecode(response.body);
    } else {
      throw Exception('Ошибка входа: ${response.body}');
    }
  }
}
