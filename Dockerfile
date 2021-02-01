FROM gradle:6.6.1-jdk11-hotspot as builder
USER root
WORKDIR /builder
ADD . /builder
RUN gradle build --stacktrace

FROM  openjdk:11-jre
WORKDIR /app
EXPOSE 8080
ARG arg_active_profile
ARG arg_auth_url
ARG arg_keycloak_realm
ARG arg_keycloak_client
ARG arg_database_url
ARG arg_database_username
ARG arg_database_password
ARG arg_ddl_auto
ARG arg_port
ARG arg_keycloak_client_secret
ARG arg_key_store_url
ARG arg_key_store_password
ARG arg_key_store_alias

ENV ACTIVE_PROFILE=$arg_active_profile
ENV AUTH_URL=$arg_auth_url
ENV KEYCLOAK_REALM=$arg_keycloak_realm
ENV KEYCLOAK_CLIENT=$arg_keycloak_client
ENV DATABASE_URL=$arg_database_url
ENV DATABASE_USERNAME=$arg_database_username
ENV DATABASE_PASSWORD=$arg_database_password
ENV DATABASE_DDL_AUTO=$arg_ddl_auto
ENV KEYCLOAK_CLIENT_SECRET=$arg_keycloak_client_secret
ENV PORT=$arg_port
ENV KEY_STORE_URL=$arg_key_store_url
ENV KEY_STORE_PASSWORD=$arg_key_store_password
ENV KEY_STORE_ALIAS=$arg_key_store_alias

COPY  --from=builder /builder/build/libs/blog-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "blog-0.0.1-SNAPSHOT.jar"]