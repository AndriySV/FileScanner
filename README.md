# FileScanner

Назва аплікації FileScanner.
Аплікація збирається за допомогою фреймворку Apache Maven.

Дана аплікація сканує весь вміст заданої папки, знаходить усі файли та групує їх по однаковому вмісту. Результат сканування виводиться на консоль. Файли або групи файлів сортуються у порядку зростання по їх розмірах. 

Після запуску програми, у консолі, необхідно ввести шлях до папки, у якій буде здійснюватись пошук та групування файлів.

Також до даної аплікації написаний тест, який тестує основну функціональність програми. Використана бібліотека TestNG.

Для зручності запуску аплікації у консолі, потрібно ввести наступні команди:

git clone https://github.com/AndriySV/FileScanner
cd FileScanner
mvn clean install exec:java -Dexec.mainClass="fileScanner.Main
