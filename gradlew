scrape_configs:
  - job_name: 'SpringBootSecurityDBMetrics'
    metrics_path: '/actuator/prometheus'
    scrape_interval: 3s
    static_configs:
      - targets: ['host.docker.internal:8080']
        labels:
          application: 'springbootsecuritydb'