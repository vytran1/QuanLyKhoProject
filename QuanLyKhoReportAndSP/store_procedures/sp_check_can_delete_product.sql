use shopmedb;
DELIMITER $$
CREATE PROCEDURE `check_can_delete_product`(IN productId INT, OUT message varchar(256))
BEGIN
    DECLARE countImporting INT;
    DECLARE countExporting INT;
    DECLARE countOrders INT;

    SET message = '';

    SELECT COUNT(*) INTO countImporting FROM importing_form_detail WHERE product_id = productId;
    SELECT COUNT(*) INTO countExporting FROM exporting_form_detail WHERE product_id = productId;
    SELECT COUNT(*) INTO countOrders FROM inventory_order_detail WHERE product_id = productId;

    IF countImporting > 0 THEN
        SET message = CONCAT(message, 'Referenced in ImportingForm_Details. ');
    END IF;

    IF countExporting > 0 THEN
        SET message = CONCAT(message, 'Referenced in ExportingForm_Details. ');
    END IF;

    IF countOrders > 0 THEN
        SET message = CONCAT(message, 'Referenced in Order_Details. ');
    END IF;
    
    IF message = '' THEN
		SET message = concat(message, "Can delete product.");
	END IF;
END$$
DELIMITER ;





CALL check_can_delete_product(25,@message);
SELECT @message;
