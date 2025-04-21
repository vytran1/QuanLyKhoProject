use shopmedb;
DELIMITER //
create procedure spHoatDongNhanVien(IN start_date datetime(6),IN end_date datetime(6),IN user_id Varchar(30))
BEGIN
	 DROP TEMPORARY TABLE IF EXISTS TEMPPN;
     DROP TEMPORARY TABLE IF EXISTS TEMPPX;
     
     /* NGAY,SOPHIEU,HOTENKH,LOAIPHIEU */
     CREATE TEMPORARY TABLE TEMPPN AS
     SELECT DATE(ip.created_time) as NGAY, ip.importing_form_id as SOPHIEU, ' ' as HOTENKH, 'NHẬP' as LOAIPHIEU FROM importing_form as ip
     WHERE ip.created_time >= start_date AND ip.created_time <= end_date AND ip.inventory_user_id = user_id
     ;
     
     CREATE TEMPORARY TABLE TEMPPX AS
     SELECT DATE(ep.created_time) as NGAY, ep.exporting_form_id as SOPHIEU, ep.customer_name as HOTENKH, 'XUẤT' as LOAIPHIEU FROM exporting_form as ep
     WHERE ep.created_time >= start_date AND ep.created_time <= end_date AND ep.inventory_user_id = user_id
     ;
     
     /*Tổng hợp thông tin */
     SELECT 
           NGAY,SOPHIEU,LOAIPHIEU,
           HOTENKH, p.name AS TENVT,
           SOLUONG,DONGIA,
           CONCAT(MONTH(TTPHIEU.NGAY), '/', YEAR(TTPHIEU.NGAY)) AS THANG 
     FROM products as p
     Inner JOIN (
        SELECT NGAY,SOPHIEU,HOTENKH,LOAIPHIEU,product_id as MAVT,quantity as SOLUONG,unit_price as DONGIA FROM importing_form_detail ipd 
        INNER JOIN TEMPPN ON TEMPPN.SOPHIEU = ipd.importing_form_id
        UNION
        SELECT NGAY,SOPHIEU,HOTENKH,LOAIPHIEU,product_id as MAVT, quantity as SOLUONT,unit_price as DONGIA FROM exporting_form_detail epd
        INNER JOIN TEMPPX ON TEMPPX.SOPHIEU = epd.exporting_form_id
     ) AS TTPHIEU ON TTPHIEU.MAVT = p.id
     ORDER BY THANG
     ;
END//


DROP PROCEDURE IF EXISTS spHoatDongNhanVien;
CALL `shopmedb`.`getInventoryOrderWithoutImportingForm`();

CALL spHoatDongNhanVien('2024-10-24 00:00:00.000000', '2024-11-28 23:59:59.999999', 'N21DCVT128');
CALL getInventoryOrderWithoutImportingForm();
