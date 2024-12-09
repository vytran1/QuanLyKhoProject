export const OrderFormValidationMessage = {
  orderId: {
    required: 'Mã đơn hàng không được để trống',
    minlength: 'Mã đơn hàng phải có độ dài ít nhất là 10',
    pattern:
      "Mã đơn hàng chỉ chứa số, chữ và một số ký tự đặc biệt như '-', '_'",
    orderIdExist: 'Mã đơn hàng đã tồn tại',
  },
  supplier: {
    required: 'Nhà cung cấp không được để trống',
    supplierRequired: 'Bắt buộc phải chọn một nhà cung cấp',
  },
  providerId: {
    required: 'Nhà cung cấp không được để trống',
    supplierRequired: 'Bắt buộc phải chọn một nhà cung cấp',
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
  inventoryId: {
    required: 'Kho không được để trống',
    supplierRequired: 'Kho không được để trống',
  },
  orderDetails: {
    minFormArrayLength: 'Đơn hàng phải có ít nhất 1 chi tiết đơn hàng',
    quantity: {
      required: 'Số lượng trong chi tiết đơn hàng không được để trống',
      pattern: 'Số lượng trong chi tiết đơn hàng phải là chữ số',
      min: 'Số lượng trong chi tiết đơn hàng phải lớn hơn 0',
    },
    unitPrice: {
      required: 'Đơn giá trong chi tiết đơn hàng không được để trống',
      pattern: 'Đơn giá trong chi tiết đơn hàng phải là chữ số',
      min: 'Đơn giá trong chi tiết đơn hàng phải lớn hơn 0',
    },
  },
};

export interface ValidationMessages {
  [key: string]: string | ValidationMessages;
}
