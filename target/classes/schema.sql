CREATE TABLE IF NOT EXISTS gold_price (
                                          id SERIAL PRIMARY KEY,
                                          date DATE NOT NULL UNIQUE,
                                          price DECIMAL(10, 2) NOT NULL
    );
