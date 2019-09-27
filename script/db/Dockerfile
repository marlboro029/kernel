FROM mysql:8.0.16

MAINTAINER kernel

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

COPY ./db/schema.sql /docker-entrypoint-initdb.d

COPY ./db/kernel.sql /docker-entrypoint-initdb.d

COPY ./db/kernel_ac.sql /docker-entrypoint-initdb.d

COPY ./db/kernel_job.sql /docker-entrypoint-initdb.d

COPY ./db/kernel_mp.sql /docker-entrypoint-initdb.d

COPY ./db/kernel_config.sql /docker-entrypoint-initdb.d

COPY ./db/kernel_pay.sql /docker-entrypoint-initdb.d

COPY ./db/kernel_codegen.sql /docker-entrypoint-initdb.d