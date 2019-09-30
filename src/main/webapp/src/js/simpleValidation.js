const simpleValidation = () => {
  const forms = document.querySelectorAll('.needs-validation');
  forms.forEach((form) => {
    form.addEventListener('submit', (e) => {
      if (form.checkValidity() === false) {
        e.preventDefault();
        e.stopPropagation();
      }
      form.classList.add('was-validated');
    });
  });
};

simpleValidation();
