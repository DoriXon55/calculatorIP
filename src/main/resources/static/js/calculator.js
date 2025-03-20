document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('method').value = 'FLSM';
    document.querySelectorAll('.ripple').forEach(function(button) {
        button.addEventListener('click', createRipple);
    });
});

function createRipple(event) {
    const button = event.currentTarget;

    const circle = document.createElement('span');
    const diameter = Math.max(button.clientWidth, button.clientHeight);
    const radius = diameter / 2;

    circle.style.width = circle.style.height = `${diameter}px`;
    circle.style.left = `${event.clientX - button.getBoundingClientRect().left - radius}px`;
    circle.style.top = `${event.clientY - button.getBoundingClientRect().top - radius}px`;
    circle.classList.add('ripple-effect');

    const ripple = button.querySelector('.ripple-effect');
    if (ripple) {
        ripple.remove();
    }

    button.appendChild(circle);
}

function calculate() {
    const method = document.getElementById('method').value;
    const ip = document.getElementById('ip').value;
    const amountOfSubnets = parseInt(document.getElementById('amountOfSubnets').value);

    if (!ip || !amountOfSubnets) {
        showError('Proszę wypełnić wszystkie wymagane pola');
        return;
    }

    const data = {
        ip: ip,
        amountOfSubnets: amountOfSubnets,
        method: method
    };

    if (method === 'VLSM') {
        const hostInputs = document.querySelectorAll('.host-requirement input');
        if (hostInputs.length === 0) {
            showError('Proszę określić liczbę podsieci');
            return;
        }

        const hostRequirements = Array.from(hostInputs).map(input => parseInt(input.value));

        if (hostRequirements.some(val => !val || val < 1)) {
            showError('Proszę wprowadzić poprawne liczby hostów dla wszystkich podsieci');
            return;
        }

        data.hostRequirements = hostRequirements;
    }

    fetch('/calculate', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(result => {
            showResult(result, false);
            document.getElementById('downloadBtn').style.display = 'block';
        })
        .catch(error => {
            showError(error.message);
            document.getElementById('downloadBtn').style.display = 'none';
        });
}

function showResult(content, isError) {
    const resultDiv = document.getElementById('result');
    resultDiv.innerHTML = content.replace(/\n/g, '<br>');
    resultDiv.classList.add('visible');

    if (isError) {
        resultDiv.classList.add('error');
    } else {
        resultDiv.classList.remove('error');
    }

    resultDiv.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

function showError(message) {
    showResult(message, true);
}

function updateSubnetInputs() {
    const method = document.getElementById('method').value;
    const amount = parseInt(document.getElementById('amountOfSubnets').value) || 0;
    const container = document.getElementById('hostRequirements');

    container.innerHTML = '';

    if (method === 'VLSM' && amount > 0) {
        container.style.display = 'block';

        for (let i = 0; i < amount; i++) {
            setTimeout(() => {
                const div = document.createElement('div');
                div.className = 'host-requirement';
                div.innerHTML = `
                    <label>Liczba hostów dla podsieci ${i + 1}:</label>
                    <div class="input-container">
                        <input type="number" min="1" placeholder="min. 1" required>
                        <span class="focus-border"></span>
                    </div>
                `;
                container.appendChild(div);
            }, i * 50);
        }
    } else {
        container.style.display = 'none';
    }

    document.getElementById('result').classList.remove('visible');
    document.getElementById('downloadBtn').style.display = 'none';
}

function downloadResults() {
    const method = document.getElementById('method').value;
    const ip = document.getElementById('ip').value;
    const amountOfSubnets = parseInt(document.getElementById('amountOfSubnets').value);

    if (!ip || !amountOfSubnets) {
        showError('Proszę wypełnić wszystkie wymagane pola przed pobraniem wyników');
        return;
    }

    const data = {
        ip: ip,
        amountOfSubnets: amountOfSubnets,
        method: method
    };

    if (method === 'VLSM') {
        const hostInputs = document.querySelectorAll('.host-requirement input');
        const hostRequirements = Array.from(hostInputs).map(input => parseInt(input.value));

        if (hostRequirements.some(val => !val || val < 1)) {
            showError('Proszę wprowadzić poprawne liczby hostów dla wszystkich podsieci');
            return;
        }

        data.hostRequirements = hostRequirements;
    }

    const downloadBtn = document.getElementById('downloadBtn');
    const originalText = downloadBtn.innerHTML;
    downloadBtn.innerHTML = '<span class="btn-text">Pobieranie...</span>';
    downloadBtn.disabled = true;

    fetch('/download', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.text();
        })
        .then(fileName => {
            const link = document.createElement('a');
            link.href = '/download/' + fileName;
            link.download = fileName;
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);

            downloadBtn.innerHTML = originalText;
            downloadBtn.disabled = false;
        })
        .catch(error => {
            showError('Wystąpił błąd podczas pobierania pliku: ' + error.message);
            downloadBtn.innerHTML = originalText;
            downloadBtn.disabled = false;
        });
}

document.addEventListener('DOMContentLoaded', function() {
    const style = document.createElement('style');
    style.innerHTML = `
        .ripple {
            position: relative;
            overflow: hidden;
        }
        
        .ripple-effect {
            position: absolute;
            border-radius: 50%;
            background-color: rgba(255, 255, 255, 0.3);
            transform: scale(0);
            animation: ripple 0.6s linear;
            pointer-events: none;
        }
        
        @keyframes ripple {
            to {
                transform: scale(2);
                opacity: 0;
            }
        }
    `;
    document.head.appendChild(style);
});