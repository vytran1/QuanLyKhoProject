export const ExportingFormValidationMessage = {
  exportingFormId: {
    required: 'Mã phiếu xuất không được để trống',
    minlength: 'Mã phiếu xuất phải có độ dài ít nhất là 10',
    pattern:
      "Mã phiếu xuất chỉ chứa số, chữ và một số ký tự đặc biệt như '-', '_'",
    exportingFormIdExists: 'Mã phiếu xuất đã tồn tại',
  },
  customerName: {
    required: 'Tên khách hàng không được để trống',
    minlength: 'Tên khách hàng phải có độ dài ít nhất là 10 ký tự',
    pattern: 'Tên khách hàng chỉ chứa chữ cái',
  },
  customerPhoneNumber: {
    required: 'Số điện thoại khách hàng không được để trống',
    pattern: 'Số điện thoại khách hàng phải có đúng 10 chữ số',
  },
  inventory: {
    required: 'Kho không được để trốngg',
    supplierRequired: 'Kho không được để trống',
  },
  exportingDetails: {
    minFormArrayLength: 'Phiếu Xuất phải có ít nhất 1 chi tiết phiếu xuất',
    quantity: {
      required: 'Số lượng trong chi tiết phiếu xuất không được để trống',
      pattern: 'Số lượng trong chi tiết phiếu xuất  phải là chữ số',
      min: 'Số lượng trong chi tiết phiếu xuất phải lớn hơn 0',
      max: 'Số lượng trong chi tiết phiếu xuất phải nhỏ hơn hoặc bằng số lượng tồn kho.',
    },
    unitPrice: {
      required: 'Đơn giá trong chi tiết phiếu xuất không được để trống',
      pattern: 'Đơn giá trong chi tiết phiếu xuất phải là chữ số',
      min: 'Đơn giá trong chi tiết phiếu xuất phải lớn hơn 0',
    },
  },
};

export interface ValidationMessages {
  [key: string]: string | ValidationMessages;
}
