// validation.js
document.addEventListener('DOMContentLoaded', () => {
    // Configurações de validação para cada formulário
    const configs = {
        loginForm: [
            { field: 'email', type: 'email', required: true },
            { field: 'senha', type: 'password', required: true, minLen: 6 }
        ],
        adminForm: [
            { field: 'nome', type: 'text', required: true },
            { field: 'email', type: 'email', required: true },
            { field: 'senha', type: 'password', required: true, minLen: 6 }
        ]
    };

    const emailRe = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    function validateField(cfg) {
        const input = document.getElementById(cfg.field);
        const errEl = document.getElementById(cfg.field + 'Error');
        if (!input || !errEl) return true;

        const label = input.previousElementSibling?.textContent.replace(':','') || cfg.field;
        let v = input.value.trim();
        let msg = '';

        // Considera o atributo required do HTML além do config JS
        const isRequired = cfg.required || input.hasAttribute('required');

        if (isRequired && !v) {
            msg = `${label} não informado!`;
        } else if (cfg.type === 'email' && v && !emailRe.test(v)) {
            msg = 'Formato de e-mail inválido!';
        } else if (cfg.minLen && v.length > 0 && v.length < cfg.minLen) {
            msg = `Mínimo ${cfg.minLen} caracteres!`;
        }

        errEl.textContent = msg;
        return msg === '';
    }

    // Aplica validação a cada formulário definido
    Object.entries(configs).forEach(([formId, rules]) => {
        const form = document.getElementById(formId);
        if (!form) return;

        // Validação ao sair do campo
        rules.forEach(cfg => {
            const input = document.getElementById(cfg.field);
            if (input) input.addEventListener('blur', () => validateField(cfg));
        });

        // Validação ao submeter o formulário
        form.addEventListener('submit', e => {
            let allOk = true;
            rules.forEach(cfg => { if (!validateField(cfg)) allOk = false; });
            if (!allOk) e.preventDefault();
        });
    });
});
