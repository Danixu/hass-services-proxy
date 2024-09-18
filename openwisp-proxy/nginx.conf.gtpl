

# Run nginx in foreground.
daemon off;

# This is run inside Docker.
user nginx;

# Pid storage location.
pid /var/run/nginx.pid;

# Set number of worker processes.
worker_processes 1;

# Enables the use of JIT for regular expressions to speed-up their processing.
pcre_jit on;

# Write error log to the add-on log.
error_log /proc/1/fd/1;

# Max num of simultaneous connections by a worker process.
events {
    worker_connections 512;
}

http {
    access_log              /dev/stdout;
    client_max_body_size    10M;
    default_type            application/octet-stream;
    keepalive_timeout       65;
    sendfile                off;
    server_tokens           off;
    tcp_nodelay             on;
    tcp_nopush              on;

    map $http_upgrade $connection_upgrade {
        default upgrade;
        ''      close;
    }
    resolver 127.0.0.11 ipv6=off;

    server {
        listen 8099 default_server;

        root /dev/null;
        server_name _;

        location / {
            allow   172.30.32.2;
            deny    all;

            absolute_redirect off;

            proxy_pass                  {{ .server }};
            proxy_http_version          1.1;
            proxy_ignore_client_abort   off;
            proxy_read_timeout          86400s;
            proxy_send_timeout          86400s;
            proxy_max_temp_file_size    0;

            proxy_set_header Accept-Encoding "";
            proxy_set_header Connection $connection_upgrade;
            {{- if .domain}}
            proxy_set_header Host "{{.domain}}";
            proxy_set_header Referer "https://{{.domain}}";
            {{- else}}
            proxy_set_header Host $http_host;
            proxy_set_header Referer $http_referer;
            {{- end}}
            {{- if not .verify_ssl}}
            proxy_ssl_verify              off;
            {{- end}}
            proxy_set_header Upgrade $http_upgrade;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;
            proxy_set_header X-NginX-Proxy true;
            proxy_set_header X-Real-IP $remote_addr;

            # Filters
            proxy_redirect '/' $http_x_ingress_path/;
            sub_filter '<form action="/'  '<form action="$http_x_ingress_path/';
            sub_filter 'href="/' 'href="$http_x_ingress_path/';
            sub_filter '<script src="/' '<script src="$http_x_ingress_path/';
            sub_filter "top.location.href='" "top.location.href='$http_x_ingress_path";

            sub_filter_once off;
        }
    }
}


