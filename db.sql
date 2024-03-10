-- Создание базы данных
CREATE DATABASE IF NOT EXISTS calculator_db;

-- Использование созданной базы данных
USE calculator_db;

-- Создание таблицы "Пользователи"
CREATE TABLE IF NOT EXISTS Users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  login VARCHAR(255) NOT NULL UNIQUE,
  username VARCHAR(255),
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  is_admin BOOLEAN NOT NULL DEFAULT FALSE,
  is_blocked BOOLEAN NOT NULL DEFAULT FALSE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Создание таблицы "Чаты"
CREATE TABLE IF NOT EXISTS Chats (
  chat_id INT AUTO_INCREMENT PRIMARY KEY,
  chatname VARCHAR(255) NOT NULL DEFAULT 'new chat',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Создание таблицы "Участники чата"
CREATE TABLE IF NOT EXISTS ChatMembers (
  chat_id INT NOT NULL,
  user_id INT NOT NULL,
  is_admin BOOLEAN NOT NULL DEFAULT FALSE,
  is_blocked BOOLEAN NOT NULL DEFAULT FALSE,
  FOREIGN KEY (chat_id) REFERENCES Chats(chat_id),
  FOREIGN KEY (user_id) REFERENCES Users(user_id),
  PRIMARY KEY (chat_id, user_id)
);

-- Создание таблицы "Сообщения"
CREATE TABLE IF NOT EXISTS Messages (
  message_id INT AUTO_INCREMENT PRIMARY KEY,
  chat_id INT NOT NULL,
  user_id INT NOT NULL,
  content TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (chat_id) REFERENCES Chats(chat_id),
  FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Создание таблицы "Примеры"
CREATE TABLE IF NOT EXISTS Examples (
  example_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  content TEXT NOT NULL,
  result TEXT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
