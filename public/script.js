'use strict';
(() => {
  const messages = [
    'Absolution denied. The killer was checking a locker in another postcode.',
    'Your confession has been forwarded to the teammate currently running through your dead zone.',
    'Three pallets in twelve seconds. Finally, a speedrun nobody asked for.',
    'The Entity appreciates your donation. Your team has requested a refund.',
    'You created distance. Mostly between your teammates and their will to continue.',
    'That pallet had a family. More importantly, it had another four generators to live for.',
    'Good news: you stunned the killer. Emotionally. With your decision-making.',
    'Forgiveness is a limited resource. Much like the pallets you just used.'
  ];
  const button = document.querySelector('#confess');
  const response = document.querySelector('#confession');
  let previous = -1;
  button.addEventListener('click', () => {
    // Pick from all messages except the last one, so every click changes the response.
    let next = Math.floor(Math.random() * (messages.length - 1));
    if (next >= previous && previous !== -1) next += 1;
    if (previous === -1) next = Math.floor(Math.random() * messages.length);
    previous = next;
    response.textContent = messages[next];
    response.classList.add('revealed');
    response.classList.remove('flash');
    void response.offsetWidth;
    response.classList.add('flash');
  });
})();
