USE shopmedb;
DELIMITER //
CREATE PROCEDURE spTongHopNhapXuat(IN startDate DATETIME(6), IN endDate DATETIME(6))
BEGIN  
     DROP TEMPORARY TABLE IF EXISTS TEMPPN;
     DROP TEMPORARY TABLE IF EXISTS TEMPPX;
     DROP TEMPORARY TABLE IF EXISTS TEMP_LEFT_JOIN;
     DROP TEMPORARY TABLE IF EXISTS TEMP_RIGHT_JOIN;
     
     CREATE TEMPORARY TABLE TEMPPN AS
     SELECT 
         PN.created_time AS NGAY, 
         SUM(CT.quantity * CT.unit_price) AS NHAP,
         (SUM(CT.quantity * CT.unit_price) / (SELECT SUM(unit_price * quantity) 
                                              FROM importing_form_detail, importing_form 
                                              WHERE importing_form_detail.importing_form_id = importing_form.importing_form_id
                                              AND importing_form.created_time BETWEEN startDate AND endDate)) AS TYLENHAP
     FROM importing_form AS PN
     JOIN importing_form_detail AS CT ON PN.importing_form_id = CT.importing_form_id
     WHERE PN.created_time BETWEEN startDate AND endDate
     GROUP BY PN.created_time;
     
     CREATE TEMPORARY TABLE TEMPPX AS
     SELECT
         PX.created_time AS NGAY, 
         SUM(CTPX.quantity * CTPX.unit_price) AS XUAT, 
         (SUM(CTPX.quantity * CTPX.unit_price) / (SELECT SUM(unit_price * quantity) 
                                                  FROM exporting_form_detail, exporting_form 
                                                  WHERE exporting_form_detail.exporting_form_id = exporting_form.exporting_form_id
                                                  AND exporting_form.created_time BETWEEN startDate AND endDate)) AS TYLEXUAT
     FROM exporting_form AS PX
     JOIN exporting_form_detail AS CTPX ON PX.exporting_form_id = CTPX.exporting_form_id
     WHERE PX.created_time BETWEEN startDate AND endDate
     GROUP BY PX.created_time;
     
     -- Lưu kết quả của LEFT JOIN vào TEMP_LEFT_JOIN
     CREATE TEMPORARY TABLE TEMP_LEFT_JOIN AS
     SELECT 
         COALESCE(PN.NGAY, PX.NGAY) AS NGAY,
         COALESCE(PN.NHAP, 0) AS NHAP,
         COALESCE(PN.TYLENHAP, 0) AS TILENHAP,
         COALESCE(PX.XUAT, 0) AS XUAT,
         COALESCE(PX.TYLEXUAT, 0) AS TILEXUAT
     FROM TEMPPN AS PN
     LEFT JOIN TEMPPX AS PX ON PN.NGAY = PX.NGAY;
     
     -- Lưu kết quả của RIGHT JOIN vào TEMP_RIGHT_JOIN
     CREATE TEMPORARY TABLE TEMP_RIGHT_JOIN AS
     SELECT 
         COALESCE(PN.NGAY, PX.NGAY) AS NGAY,
         COALESCE(PN.NHAP, 0) AS NHAP,
         COALESCE(PN.TYLENHAP, 0) AS TILENHAP,
         COALESCE(PX.XUAT, 0) AS XUAT,
         COALESCE(PX.TYLEXUAT, 0) AS TILEXUAT
     FROM TEMPPN AS PN
     RIGHT JOIN TEMPPX AS PX ON PN.NGAY = PX.NGAY;
     
     -- Hợp nhất kết quả từ TEMP_LEFT_JOIN và TEMP_RIGHT_JOIN
     SELECT * FROM TEMP_LEFT_JOIN
     UNION ALL
     SELECT * FROM TEMP_RIGHT_JOIN
     ORDER BY NGAY;
     
     -- Xóa các bảng tạm sau khi sử dụng
     DROP TEMPORARY TABLE IF EXISTS TEMP_LEFT_JOIN;
     DROP TEMPORARY TABLE IF EXISTS TEMP_RIGHT_JOIN;
     
END //
DELIMITER ;

-- Xóa thủ tục nếu tồn tại và gọi lại
DROP PROCEDURE IF EXISTS spTongHopNhapXuat;

CALL spTongHopNhapXuat('2024-10-24 00:00:00.000000', '2024-11-28 23:59:59.999999');
