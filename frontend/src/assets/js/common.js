// 전역 변수 설치

import Swal from "sweetalert2";

export const swal = (position, timer, icon, title) => {
  const Toast = Swal.mixin({
    toast: true,
    position: position,
    showConfirmButton: false,
    timer: timer,
    timerProgressBar: true,
  });

  Toast.fire({
    icon: icon,
    title: title,
  });
}