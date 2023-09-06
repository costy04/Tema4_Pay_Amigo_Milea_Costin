-- Crearea tabelului 'my_user'
CREATE TABLE my_user (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Crearea tabelului 'wallet'
CREATE TABLE wallet (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    user_id BIGINT,
    balance DOUBLE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES my_user(user_id)
);

-- Crearea tabelului 'transaction'
CREATE TABLE transaction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    source_wallet_id BIGINT,
    destination_wallet_id BIGINT,
    amount DOUBLE,
    FOREIGN KEY (source_wallet_id) REFERENCES wallet (id),
    FOREIGN KEY (destination_wallet_id) REFERENCES wallet (id)
);

-- Crearea tabelului 'transaction'
CREATE TABLE my_user_seq (
    next_val BIGINT AUTO_INCREMENT PRIMARY KEY
);
