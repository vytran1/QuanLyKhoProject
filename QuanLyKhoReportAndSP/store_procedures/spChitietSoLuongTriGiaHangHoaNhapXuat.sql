use shopmedb;
DELIMITER //
CREATE Procedure spChitietSoLuongTriGiaHangHoaNhapXuat(IN type VARCHAR(10), IN startDate DATETIME(6), IN endDate DATETIME(6))
BEGIN 
     IF type = 'NHAP' THEN
		SELECT DATE_FORMAT(created_time,'%m-%y') AS THANGNAM,
               vt.name AS TENVT,
               SUM(CT.quantity) AS TONGSOLUONG,
               SUM(CT.quantity * CT.unit_price) AS TRIGIA
        FROM
            (SELECT created_time,importing_form_id FROM importing_form WHERE created_time between startDate AND endDate ) as PHIEU,
            (SELECT name,id FROM products) as VT,
            (SELECT quantity, unit_price, importing_form_id, product_id FROM importing_form_detail) as CT
        WHERE PHIEU.importing_form_id  = CT.importing_form_id 
        AND   VT.id = CT.product_id
        GROUP BY DATE_FORMAT(created_time,'%m-%y'),TENVT
        ORDER BY DATE_FORMAT(created_time,'%m-%y'),TENVT;
     ELSE 
        SELECT DATE_FORMAT(created_time,'%m-%y') AS THANGNAM,
               vt.name AS TENVT,
               SUM(CT.quantity) AS TONGSOLUONG,
               SUM(CT.quantity * CT.unit_price) AS TRIGIA
        FROM
              (SELECT created_time,exporting_form_id FROM exporting_form WHERE created_time between startDate AND endDate ) AS PHIEU,
			  (SELECT name,id FROM products) as VT,
			  (SELECT quantity,unit_price,exporting_form_id,product_id FROM exporting_form_detail) AS CT
        WHERE PHIEU.exporting_form_id = CT.exporting_form_id
        AND VT.id = CT.product_id
		GROUP BY DATE_FORMAT(created_time,'%m-%y'),TENVT
        ORDER BY DATE_FORMAT(created_time,'%m-%y'),TENVT;
     END IF;   
END //

DROP PROCEDURE IF EXISTS spChitietSoLuongTriGiaHangHoaNhapXuat;

CALL spChitietSoLuongTriGiaHangHoaNhapXuat('XUAT','2024-10-24 00:00:00.000000','2024-11-28 23:59:59.999999')