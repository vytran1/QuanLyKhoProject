export const ImportingFormValidationMessage = {
  importingFormId: {
    required: 'Mã phiếu nhập không được để trống',
    minlength: 'Mã phiếu nhập phải có độ dài ít nhất là 10',
    pattern:
      "Mã phiếu nhập chỉ chứa số, chữ và một số ký tự đặc biệt như '-', '_'",
    importingFormIdExists: 'Mã phiếu nhập đã tồn tại',
  },
  inventory: {
    required: 'Kho không được để trống',
    supplierRequired: 'Kho không được để trống',
  },
  importingDetails: {
    minFormArrayLength: 'Phiếu nhập phải có ít nhất 1 chi tiết phiếu nhập',
    quantity: {
      required: 'Số lượng trong chi tiết phiếu nhập không được để trống',
      pattern: 'Số lượng trong chi tiết phiếu nhập phải là chữ số',
      min: 'Số lượng trong chi tiết phiếu nhập phải lớn hơn 0',
      quantityExceeds:
        ' Số lượng bạn nhập vượt quá so với số lượng được yêu cầu. Vui lòng kiểm tra lại đơn hàng',
    },
    unitPrice: {
      required: 'Đơn giá trong chi tiết phiếu nhập không được để trống',
      pattern: 'Đơn giá trong chi tiết phiếu nhập phải là chữ số',
      min: 'Đơn giá trong chi tiết phiếu nhập phải lớn hơn 0',
    },
  },
};

export interface ValidationMessages {
  [key: string]: string | ValidationMessages;
}
