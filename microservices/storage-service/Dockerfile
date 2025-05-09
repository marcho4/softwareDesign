FROM lukemathwalker/cargo-chef:latest-rust-1 AS chef
WORKDIR /app

FROM chef AS planner
COPY . .
RUN cargo chef prepare --recipe-path recipe.json

FROM chef AS builder
COPY --from=planner /app/recipe.json recipe.json
RUN apt-get update && \
    apt-get install -y cmake pkg-config libssl-dev && \
    rm -rf /var/lib/apt/lists/*
RUN cargo chef cook --release --recipe-path recipe.json
COPY . .
RUN cargo build --release --bin storage-service

FROM debian:bookworm-slim AS runtime
WORKDIR /app

RUN apt-get update && \
    apt-get install -y libssl3 ca-certificates && \
    rm -rf /var/lib/apt/lists/*

COPY --from=builder /app/target/release/storage-service /app/storage-service
COPY --from=builder /app/src/migrations/20230101000000_initial_schema.sql /app/src/migrations/20230101000000_initial_schema.sql
RUN mkdir -p /app/content

EXPOSE 8001
CMD ["./storage-service"]