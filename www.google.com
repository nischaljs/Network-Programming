127.0.0.1 - - [10/Oct/2025:13:55:36 +0545] "GET /index.html HTTP/1.1" 200 2326 "http://localhost/" "Mozilla/5.0"
192.168.1.5 - user1 [10/Oct/2025:13:56:01 +0545] "POST /login HTTP/1.1" 302 45 "" "Chrome/118.0.0.0"
203.0.113.4 - - [10/Oct/2025:13:56:22 +0545] "GET /images/logo.png HTTP/1.1" 200 15400 "-" "Mozilla/5.0"
127.0.0.1 - - [10/Oct/2025:13:57:15 +0545] "GET /non-existent-page.html HTTP/1.1" 404 312 "-" "Mozilla/5.0"
