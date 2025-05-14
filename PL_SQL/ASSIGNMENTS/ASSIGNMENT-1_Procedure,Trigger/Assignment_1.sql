create database hex_assignments; 

use hex_assignments;

CREATE TABLE book(
	bid INT PRIMARY KEY auto_increment,
    btitle VARCHAR(255),
    bprice DOUBLE,
    bpublication_house VARCHAR(255),
    bcategory VARCHAR(255),
    bbook_count INT,
    bstatus VARCHAR(255)
);



INSERT INTO book(btitle, bprice, bpublication_house, bcategory, bbook_count, bstatus) VALUES
('Harry Potter and the Philosopher\'s Stone', 500, 'Bloomsbury', 'fiction', 30, 'IN-STOCK'),
('Battle of the Wand Wars', 480, 'Scholastic', 'war', 15, 'OUT-OF-STOCK'),
('Wizard World Cup', 390, 'Warner Bros', 'sports', 25, 'IN-STOCK'),
('Hogwarts Comedy Nights', 340, 'Penguin', 'comedy', 20, 'IN-STOCK'),
('Harry Potter and the Goblet of Fire', 620, 'Bloomsbury', 'fiction', 22, 'OUT-OF-STOCK'),
('Dumbledore’s Last Stand', 570, 'HarperCollins', 'war', 18, 'IN-STOCK'),
('Weasley Joke Shop Stories', 350, 'Scholastic', 'comedy', 12, 'IN-STOCK'),
('Quidditch Glory', 410, 'Warner Bros', 'sports', 19, 'OUT-OF-STOCK'),
('Harry Potter and the Deathly Hallows', 700, 'Bloomsbury', 'fiction', 28, 'IN-STOCK'),
('The Great Wizarding War', 530, 'Penguin', 'war', 13, 'OUT-OF-STOCK'),
('The Daily Prophet’s Funniest Moments', 300, 'Scholastic', 'comedy', 10, 'IN-STOCK'),
('Wizard Athletics Yearbook', 430, 'HarperCollins', 'sports', 11, 'OUT-OF-STOCK'),
('The Cursed Child', 620, 'Scholastic', 'fiction', 21, 'IN-STOCK'),
('Auror Missions and Battles', 560, 'Warner Bros', 'war', 16, 'IN-STOCK'),
('The Comedy of Magical Errors', 370, 'Penguin', 'comedy', 9, 'OUT-OF-STOCK'),
('Magical Olympics 2024', 460, 'HarperCollins', 'sports', 14, 'IN-STOCK'),
('Harry Potter and the Half-Blood Prince', 650, 'Bloomsbury', 'fiction', 23, 'IN-STOCK'),
('Tales from the Wizarding Frontline', 490, 'HarperCollins', 'war', 17, 'OUT-OF-STOCK'),
('Hogsmeade Humor Digest', 320, 'Scholastic', 'comedy', 8, 'IN-STOCK'),
('Triwizard Training Manual', 400, 'Penguin', 'sports', 6, 'IN-STOCK');




-- procedure for fetching all books which has bstatus='IN-STOCK' and cost under the given value

DELIMITER $$

CREATE PROCEDURE proc_instock_under_cost(IN p_cost DOUBLE)
BEGIN
	SELECT * from book where bstatus='IN-STOCK' and bprice<p_cost;
END;



-- Delete books that are from given publication houses

DELIMITER $$

CREATE PROCEDURE proc_delete_books_from_house(IN p_house VARCHAR(255))
BEGIN
	DECLARE i INT default 0;
    DECLARE no_of_rows INT;
    DECLARE id INT;
    
    SELECT count(bid) into no_of_rows from book where bpublication_house=p_house;
    
    WHILE i<no_of_rows DO
		SELECT bid into id from book where bpublication_house=p_house LIMIT i,1;
        DELETE from book where bid=id;
        SET i=i+1;
    END WHILE;
END;




-- update book price based on category

DELIMITER $$

CREATE PROCEDURE proc_update_books_from_category(IN p_category VARCHAR(255),IN p_percent DOUBLE)
BEGIN
	DECLARE i INT default 0;
    DECLARE no_of_rows INT;
    DECLARE id INT;
    
    SELECT count(bid) into no_of_rows from book where bcategory=p_category;
    
    WHILE i<no_of_rows DO
		SELECT bid into id from book where bcategory=p_category LIMIT i,1;
        UPDATE book 
        SET bprice=bprice + (bprice*(p_percent/100)) where bid=id;
        SET i=i+1;
    END WHILE;
    
END;



-- Trigger for logging

CREATE TABLE book_log(
	id INT PRIMARY KEY AUTO_INCREMENT,
    bid INT,
    date_of_op date,
    user VARCHAR(255)
);

-- Trigger for update

DELIMITER $$

CREATE TRIGGER trg_book_update
BEFORE UPDATE ON book
FOR EACH ROW
BEGIN
	INSERT INTO book_log(bid,date_of_op,user) values(OLD.bid,now(),user());
END;

-- Trigger for insert

DELIMITER $$

CREATE TRIGGER trg_book_insert
AFTER INSERT ON book
FOR EACH ROW
BEGIN
	INSERT INTO book_log(bid,date_of_op,user) values(NEW.bid,now(),user());
END;


-- Trigger for delete

DELIMITER $$

CREATE TRIGGER trg_book_delete
BEFORE DELETE ON book
FOR EACH ROW
BEGIN
	INSERT INTO book_log(bid,date_of_op,user) values(OLD.bid,now(),user());
END;


-- Calling the procedures

CALL proc_instock_under_cost(500);

CALL proc_delete_books_from_house('Penguin');

CALL proc_update_books_from_category('sports',2);