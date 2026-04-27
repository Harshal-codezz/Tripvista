/* ============================================================
   TripVista — Main JavaScript
   All interactive features and dynamic functionality
   ============================================================ */

document.addEventListener('DOMContentLoaded', () => {

  // ---- HELPER: Switch search widget tab ----
  function switchTab(tabName) {
    const tabBtns = document.querySelectorAll('.tab-btn');
    const tabContents = document.querySelectorAll('.tab-content');

    tabBtns.forEach(b => {
      b.classList.remove('active');
      b.setAttribute('aria-selected', 'false');
    });
    tabContents.forEach(content => {
      content.classList.remove('active');
    });

    const targetBtn = document.querySelector(`.tab-btn[data-tab="${tabName}"]`);
    const targetContent = document.getElementById(`content-${tabName}`);

    if (targetBtn) {
      targetBtn.classList.add('active');
      targetBtn.setAttribute('aria-selected', 'true');
    }
    if (targetContent) {
      targetContent.classList.add('active');
    }
  }


  // ---- 1. TAB SWITCHING IN SEARCH WIDGET ----
  const tabBtns = document.querySelectorAll('.tab-btn');
  const tabContents = document.querySelectorAll('.tab-content');

  tabBtns.forEach(btn => {
    btn.addEventListener('click', () => {
      const targetTab = btn.dataset.tab;
      switchTab(targetTab);
    });
  });


  // ---- 1b. NAV LINK TAB SWITCHING ----
  // Desktop and mobile nav links with data-tab switch the hero search widget tab
  const navLinksWithTab = document.querySelectorAll('.nav-link[data-tab], .mobile-nav-link[data-tab]');

  navLinksWithTab.forEach(link => {
    link.addEventListener('click', (e) => {
      const tabName = link.dataset.tab;
      if (tabName) {
        // Update active state on nav links
        document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
        document.querySelectorAll('.mobile-nav-link').forEach(l => l.classList.remove('active'));

        // Set active on the clicked nav link (and its desktop/mobile counterpart)
        document.querySelectorAll(`.nav-link[data-tab="${tabName}"]`).forEach(l => l.classList.add('active'));
        document.querySelectorAll(`.mobile-nav-link[data-tab="${tabName}"]`).forEach(l => l.classList.add('active'));

        // Switch the search widget tab
        switchTab(tabName);
      }
    });
  });


  // ---- 2. STICKY NAVBAR ON SCROLL ----
  const navbar = document.getElementById('navbar');
  const scrollThreshold = 80;

  function handleNavbarScroll() {
    if (navbar) {
      if (window.scrollY > scrollThreshold) {
        navbar.classList.add('scrolled');
      } else {
        navbar.classList.remove('scrolled');
      }
    }
  }
  window.addEventListener('scroll', handleNavbarScroll);
  // Run on load in case page is already scrolled
  handleNavbarScroll();


  // ---- 3. COUNTDOWN TIMERS ON DEAL CARDS ----
  const dealTimers = document.querySelectorAll('.deal-timer');

  dealTimers.forEach(timer => {
    let hours = parseInt(timer.dataset.hours) || 0;
    let minutes = parseInt(timer.dataset.minutes) || 0;
    let seconds = parseInt(timer.dataset.seconds) || 0;

    // Convert everything to total seconds
    let totalSeconds = (hours * 3600) + (minutes * 60) + seconds;
    const display = timer.querySelector('.timer-display');

    const interval = setInterval(() => {
      if (totalSeconds <= 0) {
        clearInterval(interval);
        display.textContent = '00:00:00';
        display.style.color = '#999';
        return;
      }

      totalSeconds--;
      const h = Math.floor(totalSeconds / 3600);
      const m = Math.floor((totalSeconds % 3600) / 60);
      const s = totalSeconds % 60;

      display.textContent =
        String(h).padStart(2, '0') + ':' +
        String(m).padStart(2, '0') + ':' +
        String(s).padStart(2, '0');
    }, 1000);
  });


  // ---- 4. SMOOTH SCROLL NAVIGATION ----
  document.querySelectorAll('a[href^="#"]').forEach(link => {
    link.addEventListener('click', (e) => {
      const targetId = link.getAttribute('href');
      if (targetId === '#') return;

      const target = document.querySelector(targetId);
      if (target) {
        e.preventDefault();
        const offset = navbar ? navbar.offsetHeight + 10 : 80;
        const targetPosition = target.getBoundingClientRect().top + window.scrollY - offset;
        window.scrollTo({ top: targetPosition, behavior: 'smooth' });

        // Close mobile drawer if open
        closeMobileDrawer();
      }
    });
  });


  // ---- 5. MOBILE HAMBURGER MENU ----
  const hamburgerBtn = document.getElementById('hamburgerBtn');
  const mobileDrawer = document.getElementById('mobileDrawer');

  // Create overlay element
  const overlay = document.createElement('div');
  overlay.className = 'mobile-overlay';
  document.body.appendChild(overlay);

  function openMobileDrawer() {
    if (!hamburgerBtn || !mobileDrawer) return;
    hamburgerBtn.classList.add('active');
    hamburgerBtn.setAttribute('aria-expanded', 'true');
    mobileDrawer.classList.add('open');
    overlay.classList.add('active');
    document.body.style.overflow = 'hidden';
  }

  function closeMobileDrawer() {
    if (!hamburgerBtn || !mobileDrawer) return;
    hamburgerBtn.classList.remove('active');
    hamburgerBtn.setAttribute('aria-expanded', 'false');
    mobileDrawer.classList.remove('open');
    overlay.classList.remove('active');
    document.body.style.overflow = '';
  }

  if (hamburgerBtn) {
    hamburgerBtn.addEventListener('click', () => {
      if (mobileDrawer && mobileDrawer.classList.contains('open')) {
        closeMobileDrawer();
      } else {
        openMobileDrawer();
      }
    });
  }

  overlay.addEventListener('click', closeMobileDrawer);

  // Close drawer on mobile link click
  document.querySelectorAll('.mobile-nav-link').forEach(link => {
    link.addEventListener('click', closeMobileDrawer);
  });


  // ---- 6. FROM-TO CITY SWAP BUTTON ----
  const swapBtn = document.getElementById('swapCities');
  const flightFrom = document.getElementById('flightFrom');
  const flightTo = document.getElementById('flightTo');

  if (swapBtn && flightFrom && flightTo) {
    swapBtn.addEventListener('click', () => {
      const temp = flightFrom.value;
      flightFrom.value = flightTo.value;
      flightTo.value = temp;

      // Add rotation animation class
      swapBtn.style.transition = 'transform 0.4s cubic-bezier(0.4,0,0.2,1)';
      swapBtn.style.transform = 'rotate(180deg)';
      setTimeout(() => {
        swapBtn.style.transform = 'rotate(0deg)';
      }, 400);
    });
  }


  // ---- 7. DATE PICKER — SET MIN DATE TO TODAY ----
  const dateInputs = document.querySelectorAll('input[type="date"]');
  const today = new Date().toISOString().split('T')[0];
  dateInputs.forEach(input => {
    input.setAttribute('min', today);
  });


  // ---- 8. SCROLL-TO-TOP BUTTON ----
  const scrollTopBtn = document.getElementById('scrollTopBtn');

  if (scrollTopBtn) {
    window.addEventListener('scroll', () => {
      if (window.scrollY > 300) {
        scrollTopBtn.classList.add('visible');
      } else {
        scrollTopBtn.classList.remove('visible');
      }
    });

    scrollTopBtn.addEventListener('click', () => {
      window.scrollTo({ top: 0, behavior: 'smooth' });
    });
  }


  // ---- 9. ANIMATED COUNTERS (Intersection Observer) ----
  const counters = document.querySelectorAll('.counter');

  const counterObserver = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        const counter = entry.target;
        const target = parseInt(counter.dataset.target);
        const originalText = counter.textContent;
        const duration = 2000; // 2 seconds
        const startTime = performance.now();

        function updateCounter(currentTime) {
          const elapsed = currentTime - startTime;
          const progress = Math.min(elapsed / duration, 1);

          // Ease-out curve
          const easeOut = 1 - Math.pow(1 - progress, 3);
          const currentVal = Math.floor(easeOut * target);

          // Format number to Crore format
          if (target >= 10000000) {
            const crores = (currentVal / 10000000).toFixed(0);
            counter.textContent = crores === '0' ? '0' : `${crores} Crore+`;
          } else {
            counter.textContent = currentVal.toLocaleString('en-IN');
          }

          if (progress < 1) {
            requestAnimationFrame(updateCounter);
          } else {
            counter.textContent = originalText;
          }
        }

        requestAnimationFrame(updateCounter);
        counterObserver.unobserve(counter);
      }
    });
  }, { threshold: 0.5 });

  counters.forEach(counter => counterObserver.observe(counter));


  // ---- 10. FADE-IN ON SCROLL (Intersection Observer) ----
  const fadeElements = document.querySelectorAll('.fade-in-up');

  const fadeObserver = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('visible');
        fadeObserver.unobserve(entry.target);
      }
    });
  }, { threshold: 0.1, rootMargin: '0px 0px -40px 0px' });

  fadeElements.forEach(el => fadeObserver.observe(el));


  // ---- 11. RADIO PILL SWITCHING (Flight trip type) ----
  const radioPills = document.querySelectorAll('.radio-pill');
  radioPills.forEach(pill => {
    pill.addEventListener('click', () => {
      // Remove active from all pills in same group
      const parent = pill.closest('.radio-pills');
      parent.querySelectorAll('.radio-pill').forEach(p => p.classList.remove('active'));
      pill.classList.add('active');
    });
  });


  // ---- 12. SEARCH BUTTON — LOADING + NAVIGATE TO RESULTS ----
  // Determine base path for navigation (pages are in /pages/ subdirectory)
  const isInPagesDir = window.location.pathname.includes('/pages/');
  const basePath = isInPagesDir ? '' : 'pages/';

  const searchFormMap = {
    'searchFlightsBtn': `${basePath}flights.html`,
    'searchHotelsBtn': `${basePath}hotels.html`,
    'searchTrainsBtn': `${basePath}trains.html`,
    'searchBusesBtn': `${basePath}buses.html`,
    'searchPackagesBtn': `${basePath}packages.html`,
  };

  Object.entries(searchFormMap).forEach(([btnId, targetPage]) => {
    const btn = document.getElementById(btnId);
    if (btn) {
      btn.addEventListener('click', (e) => {
        e.preventDefault();
        btn.classList.add('loading');
        btn.disabled = true;

        // Show loading spinner, then navigate to results page
        setTimeout(() => {
          window.location.href = targetPage;
        }, 1200);
      });
    }
  });


  // ---- 13. NEWSLETTER SUBSCRIBE ----
  const subscribeBtn = document.getElementById('subscribeBtn');
  const newsletterEmail = document.getElementById('newsletterEmail');

  if (subscribeBtn && newsletterEmail) {
    subscribeBtn.addEventListener('click', () => {
      const email = newsletterEmail.value.trim();
      if (email && email.includes('@')) {
        subscribeBtn.textContent = '✓ Subscribed!';
        subscribeBtn.style.background = '#00C851';
        subscribeBtn.style.borderColor = '#00C851';
        newsletterEmail.value = '';
        setTimeout(() => {
          subscribeBtn.textContent = 'Subscribe';
          subscribeBtn.style.background = '';
          subscribeBtn.style.borderColor = '';
        }, 3000);
      }
    });
  }

});
