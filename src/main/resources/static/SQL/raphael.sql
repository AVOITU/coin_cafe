DROP DATABASE IF EXISTS coin_cafe;

-- MySQL / MariaDB
CREATE DATABASE coin_cafe DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE coin_cafe;

-- Base tables
CREATE TABLE QUESTIONS (
                           result_id             INT UNSIGNED NOT NULL AUTO_INCREMENT,
                           question_text         VARCHAR(200) NOT NULL,
                           question_total_votes  INT NOT NULL,
                           chatgpt_comments      VARCHAR(2000) NULL,
                           all_votes_count       INT NOT NULL DEFAULT 0,
                           PRIMARY KEY (result_id)
) ENGINE=InnoDB;

CREATE TABLE SCORES (
                        score_id         INT UNSIGNED NOT NULL AUTO_INCREMENT,
                        score            INT NOT NULL,
                        score_vote_count INT NOT NULL DEFAULT 0,
                        PRIMARY KEY (score_id)
) ENGINE=InnoDB;

ALTER TABLE SCORES
    ADD CONSTRAINT ck_scores_score_range
        CHECK (score BETWEEN 0 AND 5);

CREATE TABLE PERIODS (
                         period_id          INT UNSIGNED NOT NULL AUTO_INCREMENT,
                         timestamp_period   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         period_total_votes DECIMAL(4,2) NULL,
                         PRIMARY KEY (period_id)
) ENGINE=InnoDB;

-- Junctions (no FKs yet)
-- (a.k.a. SCORES ↔ QUESTIONS)
CREATE TABLE NOTES_RESULTS (
                               note_id   INT UNSIGNED NOT NULL,  -- refers to SCORES.score_id
                               result_id INT UNSIGNED NOT NULL,  -- refers to QUESTIONS.result_id
                               PRIMARY KEY (note_id, result_id)
) ENGINE=InnoDB;

-- (a.k.a. QUESTIONS ↔ PERIODS)
CREATE TABLE RESULTS_PERIODS (
                                 result_id INT UNSIGNED NOT NULL,  -- refers to QUESTIONS.result_id
                                 period_id INT UNSIGNED NOT NULL,  -- refers to PERIODS.period_id
                                 PRIMARY KEY (result_id, period_id)
) ENGINE=InnoDB;

-- Indexes helpful on child columns (optional but explicit)
CREATE INDEX ix_notes_results_result  ON NOTES_RESULTS(result_id);
CREATE INDEX ix_results_periods_per   ON RESULTS_PERIODS(period_id);

-- Foreign keys added after table creation
ALTER TABLE NOTES_RESULTS
    ADD CONSTRAINT fk_notes_results_note
        FOREIGN KEY (note_id)  REFERENCES SCORES(score_id)
            ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT fk_notes_results_result
    FOREIGN KEY (result_id) REFERENCES QUESTIONS(result_id)
    ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE RESULTS_PERIODS
    ADD CONSTRAINT fk_results_periods_result
        FOREIGN KEY (result_id)  REFERENCES QUESTIONS(result_id)
            ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT fk_results_periods_period
    FOREIGN KEY (period_id)  REFERENCES PERIODS(period_id)
    ON DELETE CASCADE ON UPDATE CASCADE;

-- Jeu d’essai minimal pour la BDD sondage

-- TABLE QUESTIONS
INSERT INTO QUESTIONS (question_text, question_total_votes, chatgpt_comments, all_votes_count)
VALUES
    ('Hygiène du coin café', 25, 'Globalement propre, quelques points à améliorer.', 25),
    ('Accueil du personnel', 27, 'Accueil chaleureux et professionnel.', 27),
    ('Ambiance générale', 22, 'Bonne atmosphère, parfois un peu bruyante.', 22),
    ('Accessibilité du lieu', 23, 'Bonne accessibilité, signalétique claire.', 23),
    ('Qualité du service', 28, 'Service rapide et agréable.', 28);

-- TABLE SCORES
INSERT INTO SCORES (score, score_vote_count)
VALUES
    (1, 8),
    (2, 12),
    (3, 22),
    (4, 28),
    (5, 30);

-- TABLE PERIODS
INSERT INTO PERIODS (timestamp_period, period_total_votes)
VALUES
    ('2025-01-01 00:00:00', 3.2),
    ('2025-02-01 00:00:00', 3.6),
    ('2025-03-01 00:00:00', 3.9),
    ('2025-04-01 00:00:00', 4.1),
    ('2025-05-01 00:00:00', 4.3);

-- TABLE NOTES_RESULTS (liaison SCORES ↔ QUESTIONS)
INSERT INTO NOTES_RESULTS (note_id, result_id)
VALUES
    (1, 1),
    (2, 1),
    (3, 2),
    (4, 3),
    (5, 4),
    (3, 5);

-- TABLE RESULTS_PERIODS (liaison QUESTIONS ↔ PERIODS)
INSERT INTO RESULTS_PERIODS (result_id, period_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (3, 3),
    (4, 4),
    (5, 5);