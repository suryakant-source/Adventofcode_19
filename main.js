/* ============================================
   JAY BHAVANI ENTERPRISES - JavaScript
   Advanced Hero Animations + Interactions
   ============================================ */

document.addEventListener('DOMContentLoaded', function () {

    // ============================================
    // HERO ANIMATIONS
    // ============================================

    // Create floating golden particles
    function createParticles() {
        const hero = document.querySelector('.hero');
        if (!hero) return;

        const particleContainer = document.createElement('div');
        particleContainer.className = 'particle-container';
        hero.appendChild(particleContainer);

        function createParticle() {
            const particle = document.createElement('div');
            particle.className = 'golden-particle';

            // Random properties
            const startX = Math.random() * 100;
            const size = Math.random() * 6 + 2;
            const duration = Math.random() * 4 + 4;
            const delay = Math.random() * 2;

            particle.style.cssText = `
                left: ${startX}%;
                width: ${size}px;
                height: ${size}px;
                animation-duration: ${duration}s;
                animation-delay: ${delay}s;
            `;

            particleContainer.appendChild(particle);

            // Remove particle after animation
            setTimeout(() => {
                particle.remove();
            }, (duration + delay) * 1000);
        }

        // Create initial particles
        for (let i = 0; i < 30; i++) {
            setTimeout(() => createParticle(), i * 100);
        }

        // Continuously create new particles
        setInterval(createParticle, 200);
    }

    // Text animation - letter by letter with gold glow
    function animateHeroTitle() {
        const titleElement = document.querySelector('.hero-title');
        if (!titleElement) return;

        const originalHTML = titleElement.innerHTML;
        const textContent = titleElement.textContent;

        // Skip if already animated
        if (titleElement.classList.contains('text-animated')) return;
        titleElement.classList.add('text-animated');

        // Create wrapper for animation
        titleElement.innerHTML = '';
        titleElement.style.opacity = '1';

        let charIndex = 0;
        const chars = originalHTML.split('');
        let htmlBuffer = '';
        let insideTag = false;

        // Simpler approach - animate the whole title
        titleElement.innerHTML = originalHTML;
        titleElement.style.animation = 'titleReveal 1.5s ease forwards';

        // Add glow effect after reveal
        setTimeout(() => {
            titleElement.classList.add('title-glow');
        }, 1500);
    }

    // Typewriter effect for subtitle
    function typewriterEffect() {
        const subtitle = document.querySelector('.hero-subtitle');
        if (!subtitle) return;

        const text = subtitle.textContent;
        subtitle.textContent = '';
        subtitle.style.opacity = '1';
        subtitle.style.borderRight = '3px solid var(--accent)';

        let i = 0;
        const speed = 50;

        function type() {
            if (i < text.length) {
                subtitle.textContent += text.charAt(i);
                i++;
                setTimeout(type, speed);
            } else {
                // Remove cursor after typing
                setTimeout(() => {
                    subtitle.style.borderRight = 'none';
                }, 500);
            }
        }

        // Start typing after title animation
        setTimeout(type, 2000);
    }

    // Button sparkle effect on hover
    function initButtonSparkles() {
        const ctaButton = document.querySelector('.hero-cta');
        if (!ctaButton) return;

        ctaButton.addEventListener('mouseenter', function (e) {
            createSparkles(this);
        });

        // Ripple effect on click
        ctaButton.addEventListener('click', function (e) {
            const ripple = document.createElement('span');
            ripple.className = 'ripple-effect';

            const rect = this.getBoundingClientRect();
            const x = e.clientX - rect.left;
            const y = e.clientY - rect.top;

            ripple.style.left = x + 'px';
            ripple.style.top = y + 'px';

            this.appendChild(ripple);

            setTimeout(() => ripple.remove(), 600);
        });
    }

    function createSparkles(element) {
        const rect = element.getBoundingClientRect();

        for (let i = 0; i < 8; i++) {
            const sparkle = document.createElement('span');
            sparkle.className = 'button-sparkle';

            const angle = (i / 8) * Math.PI * 2;
            const distance = 30 + Math.random() * 20;
            const x = Math.cos(angle) * distance;
            const y = Math.sin(angle) * distance;

            sparkle.style.cssText = `
                --x: ${x}px;
                --y: ${y}px;
                left: 50%;
                top: 50%;
            `;

            element.appendChild(sparkle);

            setTimeout(() => sparkle.remove(), 600);
        }
    }

    // Pulsing golden badge
    function initYearBadge() {
        const yearBadge = document.querySelector('.hero-year');
        if (!yearBadge) return;

        yearBadge.classList.add('pulse-glow');
    }

    // Initialize all hero animations
    function initHeroAnimations() {
        createParticles();
        animateHeroTitle();
        typewriterEffect();
        initButtonSparkles();
        initYearBadge();
    }

    // Start hero animations
    setTimeout(initHeroAnimations, 300);

    // ============================================
    // PRODUCT CARD 3D TILT EFFECT
    // ============================================

    function initProductCardTilt() {
        const cards = document.querySelectorAll('.product-card');

        // Skip on mobile/touch devices
        if (window.matchMedia('(max-width: 768px)').matches) return;

        cards.forEach(card => {
            card.classList.add('tilting');

            card.addEventListener('mousemove', (e) => {
                const rect = card.getBoundingClientRect();
                const x = e.clientX - rect.left;
                const y = e.clientY - rect.top;
                const centerX = rect.width / 2;
                const centerY = rect.height / 2;

                // Calculate rotation (max 10 degrees)
                const rotateX = ((y - centerY) / centerY) * -10;
                const rotateY = ((x - centerX) / centerX) * 10;

                card.style.transform = `
                    perspective(1000px) 
                    rotateX(${rotateX}deg) 
                    rotateY(${rotateY}deg) 
                    translateZ(20px)
                    scale(1.02)
                `;
            });

            card.addEventListener('mouseleave', () => {
                card.style.transform = 'perspective(1000px) rotateX(0) rotateY(0) translateZ(0) scale(1)';
            });
        });
    }

    // Initialize card tilt when cards are visible
    const productsGrid = document.querySelector('.products-grid');
    if (productsGrid) {
        const tiltObserver = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    setTimeout(initProductCardTilt, 500);
                    tiltObserver.unobserve(entry.target);
                }
            });
        }, { threshold: 0.2 });
        tiltObserver.observe(productsGrid);
    }

    // ============================================
    // NAVIGATION
    // ============================================

    const mobileToggle = document.querySelector('.mobile-toggle');
    const navMenu = document.querySelector('.nav-menu');

    if (mobileToggle) {
        mobileToggle.addEventListener('click', function () {
            this.classList.toggle('active');
            navMenu.classList.toggle('active');
        });
    }

    document.querySelectorAll('.nav-link').forEach(link => {
        link.addEventListener('click', () => {
            mobileToggle?.classList.remove('active');
            navMenu?.classList.remove('active');
        });
    });

    // Header scroll effect
    const header = document.querySelector('.header');
    window.addEventListener('scroll', function () {
        if (window.scrollY > 100) {
            header?.classList.add('scrolled');
        } else {
            header?.classList.remove('scrolled');
        }
    });

    // Smooth scroll
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({ behavior: 'smooth', block: 'start' });
            }
        });
    });

    // ============================================
    // SCROLL ANIMATIONS
    // ============================================

    const observerOptions = { root: null, rootMargin: '0px', threshold: 0.1 };

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('animate-fade-up');
                observer.unobserve(entry.target);
            }
        });
    }, observerOptions);

    document.querySelectorAll('.product-card, .feature-item, .value-card, .info-card, .timeline-item').forEach(el => {
        el.style.opacity = '0';
        observer.observe(el);
    });

    // ============================================
    // STATS COUNTER WITH PROGRESS RINGS + CONFETTI
    // ============================================

    function animateCounter(element, target, duration = 2000, onComplete) {
        let start = 0;
        const increment = target / (duration / 16);

        function updateCounter() {
            start += increment;
            if (start < target) {
                element.textContent = Math.floor(start) + (element.dataset.suffix || '');
                requestAnimationFrame(updateCounter);
            } else {
                element.textContent = target + (element.dataset.suffix || '');
                element.classList.add('counted');
                if (onComplete) onComplete();
            }
        }
        updateCounter();
    }

    // Animate progress ring
    function animateProgressRing(ring, percentage) {
        const circumference = 314; // 2 * PI * 50
        const offset = circumference - (percentage / 100) * circumference;
        ring.style.setProperty('--progress-offset', offset);
        ring.classList.add('animated');
    }

    // Create confetti burst
    function createConfetti(x, y) {
        const container = document.createElement('div');
        container.className = 'confetti-container';
        document.body.appendChild(container);

        const colors = ['#FFD700', '#FF6F00', '#D32F2F', '#FF8F00', '#FFFFFF', '#FFA040'];
        const shapes = ['circle', 'square', 'triangle'];

        for (let i = 0; i < 50; i++) {
            const piece = document.createElement('div');
            piece.className = 'confetti-piece';

            const color = colors[Math.floor(Math.random() * colors.length)];
            const shape = shapes[Math.floor(Math.random() * shapes.length)];

            const startX = x + (Math.random() - 0.5) * 300;
            const startY = y;
            const angle = Math.random() * 360;

            piece.style.cssText = `
                left: ${startX}px;
                top: ${startY}px;
                background: ${color};
                ${shape === 'circle' ? 'border-radius: 50%;' : ''}
                ${shape === 'triangle' ? 'clip-path: polygon(50% 0%, 0% 100%, 100% 100%);' : ''}
                transform: rotate(${angle}deg);
                animation-delay: ${Math.random() * 0.5}s;
            `;

            container.appendChild(piece);
        }

        // Remove after animation
        setTimeout(() => container.remove(), 4000);
    }

    const statsSection = document.querySelector('.stats-section');
    if (statsSection) {
        let confettiFired = false;

        const statsObserver = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    let completed = 0;
                    const statItems = document.querySelectorAll('.stat-number');
                    const totalStats = statItems.length;

                    statItems.forEach((stat, index) => {
                        const target = parseInt(stat.dataset.target) || parseInt(stat.textContent);

                        // Animate progress ring if exists
                        const ring = stat.closest('.stat-item')?.querySelector('.stat-ring-progress');
                        if (ring) {
                            setTimeout(() => {
                                animateProgressRing(ring, 100);
                            }, index * 200);
                        }

                        // Animate counter with staggered start
                        setTimeout(() => {
                            animateCounter(stat, target, 2000, () => {
                                completed++;
                                // Fire confetti when all counters complete
                                if (completed === totalStats && !confettiFired) {
                                    confettiFired = true;
                                    const rect = statsSection.getBoundingClientRect();
                                    createConfetti(window.innerWidth / 2, rect.top + rect.height / 2);
                                }
                            });
                        }, index * 200);
                    });

                    statsObserver.unobserve(entry.target);
                }
            });
        }, { threshold: 0.5 });
        statsObserver.observe(statsSection);
    }

    // ============================================
    // FEATURE ICONS BOUNCE-IN ANIMATION
    // ============================================

    const featuresGrid = document.querySelector('.features-grid');
    if (featuresGrid) {
        const featureObserver = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    const features = entry.target.querySelectorAll('.feature-item');
                    features.forEach((feature, index) => {
                        setTimeout(() => {
                            feature.classList.add('animate-in');
                        }, index * 100);
                    });
                    featureObserver.unobserve(entry.target);
                }
            });
        }, { threshold: 0.1 });
        featureObserver.observe(featuresGrid);
    }

    // ============================================
    // FORM VALIDATION (Replaced by Animated Logic below)
    // ============================================
    // Old logic removed to prevent conflict with new features

    // ============================================
    // TIMELINE ANIMATION
    // ============================================

    const timeline = document.querySelector('.timeline');
    if (timeline) {
        const timelineObserver = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('animated');

                    // Animate items one by one
                    const items = entry.target.querySelectorAll('.timeline-item');
                    items.forEach((item, index) => {
                        setTimeout(() => {
                            item.classList.add('visible');
                        }, index * 600); // Slower stagger for storytelling effect
                    });

                    timelineObserver.unobserve(entry.target);
                }
            });
        }, { threshold: 0.2 });
        timelineObserver.observe(timeline);
    }

    // ============================================
    // STORY READING PROGRESS & HIGHLIGHTS
    // ============================================

    const storySection = document.querySelector('.story-content');
    const progressBar = document.querySelector('.reading-progress-bar');

    if (storySection && progressBar) {
        window.addEventListener('scroll', () => {
            const rect = storySection.getBoundingClientRect();
            const windowHeight = window.innerHeight;

            // Calculate progress based on scroll position relative to story content
            let progress = 0;
            if (rect.top < windowHeight) {
                const totalHeight = rect.height + windowHeight;
                const scrolled = windowHeight - rect.top;
                progress = (scrolled / totalHeight) * 100;
            }

            progressBar.style.width = `${Math.min(Math.max(progress, 0), 100)}%`;
        });

        // Highlight text reveal
        const storyObserver = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    const paragraphs = entry.target.querySelectorAll('.story-paragraph');
                    paragraphs.forEach((p, index) => {
                        setTimeout(() => {
                            p.style.opacity = '1';
                            p.style.transform = 'translateY(0)';
                        }, index * 300);
                    });
                    storyObserver.unobserve(entry.target);
                }
            });
        }, { threshold: 0.3 });
        storyObserver.observe(storySection);
    }

    // Initialize 3D tilt for new value cards (re-using existing function if compatible or adding specific one)
    if (document.querySelector('.value-card-perspective')) {
        // The CSS handles the hover flip, but we can add subtle tilt to the container for extra effect
        const valueCards = document.querySelectorAll('.value-card-perspective');

        valueCards.forEach(card => {
            card.addEventListener('mousemove', (e) => {
                const rect = card.getBoundingClientRect();
                const x = e.clientX - rect.left;
                const y = e.clientY - rect.top;
                const centerX = rect.width / 2;
                const centerY = rect.height / 2;

                // Very subtle tilt for the entire container
                const rotateX = ((y - centerY) / centerY) * -5;
                const rotateY = ((x - centerX) / centerX) * 5;

                card.style.transform = `perspective(1000px) rotateX(${rotateX}deg) rotateY(${rotateY}deg)`;
            });

            card.addEventListener('mouseleave', () => {
                card.style.transform = 'perspective(1000px) rotateX(0) rotateY(0)';
            });
        });
    }

    // Active nav link
    const currentPage = window.location.pathname.split('/').pop() || 'index.html';
    document.querySelectorAll('.nav-link').forEach(link => {
        const href = link.getAttribute('href');
        if (href === currentPage || (currentPage === '' && href === 'index.html')) {
            link.classList.add('active');
        }
    });

    // Parallax effect
    const hero = document.querySelector('.hero');
    if (hero) {
        window.addEventListener('scroll', function () {
            const scrolled = window.scrollY;
            if (scrolled < window.innerHeight) {
                hero.style.setProperty('--scroll', scrolled * 0.5 + 'px');
            }
        });
    }

    console.log('✨ Jay Bhavani Enterprises - Ultra Premium Website Loaded');
    // ============================================
    // PRODUCTS PAGE ANIMATIONS
    // ============================================

    // 1. Animated Headers
    const animatedHeaders = document.querySelectorAll('.animated-header');
    if (animatedHeaders.length > 0) {
        const headerObserver = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.querySelector('.header-icon').style.animationPlayState = 'running';
                    entry.target.querySelector('.header-underline').style.width = '100%';
                    headerObserver.unobserve(entry.target);
                }
            });
        }, { threshold: 0.5 });

        animatedHeaders.forEach(header => {
            // Reset initial state
            header.querySelector('.header-underline').style.width = '0';
            headerObserver.observe(header);
        });
    }

    // 2. Category Filtering with Wave Animation
    const filterBtns = document.querySelectorAll('.filter-btn');
    const categorySections = document.querySelectorAll('.category-section');

    if (filterBtns.length > 0) {
        // Initial wave animation on load
        animateItemsInWave();

        filterBtns.forEach(btn => {
            btn.addEventListener('click', () => {
                // Active class toggle
                filterBtns.forEach(b => b.classList.remove('active'));
                btn.classList.add('active');

                const filter = btn.dataset.filter;

                // Hide all sections first with fade out (optional, but let's just switch)
                categorySections.forEach(section => {
                    const sectionId = section.id;
                    const items = section.querySelectorAll('.image-item');

                    if (filter === 'all' || filter === sectionId) {
                        section.style.display = 'block';
                        // Reset opacity for wave
                        items.forEach(item => {
                            item.classList.remove('visible');
                            item.style.opacity = '0';
                            item.style.transform = 'translateY(20px)';
                        });
                    } else {
                        section.style.display = 'none';
                    }
                });

                // Trigger wave animation for visible items
                setTimeout(animateItemsInWave, 100);
            });
        });
    }

    function animateItemsInWave() {
        const visibleSections = Array.from(document.querySelectorAll('.category-section')).filter(s => s.style.display !== 'none');

        visibleSections.forEach(section => {
            const items = section.querySelectorAll('.image-item');
            items.forEach((item, index) => {
                setTimeout(() => {
                    item.classList.add('visible');
                    item.style.opacity = '1';
                    item.style.transform = 'translateY(0)';
                }, index * 100); // 100ms stagger
            });
        });
    }

    // 3. Quick View Modal
    const quickViewBtns = document.querySelectorAll('.quick-view-btn');
    const quickModal = document.getElementById('quick-view-modal');

    if (quickViewBtns.length > 0 && quickModal) {
        const modalImg = document.getElementById('modal-img');
        const modalTitle = document.getElementById('modal-title');
        const closeModal = quickModal.querySelector('.modal-close');

        quickViewBtns.forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.stopPropagation(); // Prevent lightbox
                const item = btn.closest('.image-item');
                const img = item.querySelector('img');
                const title = item.querySelector('h4').textContent;

                modalImg.src = img.src;
                modalTitle.textContent = title;

                quickModal.classList.add('active');
                document.body.style.overflow = 'hidden'; // Disable scroll
            });
        });

        closeModal.addEventListener('click', () => {
            quickModal.classList.remove('active');
            document.body.style.overflow = '';
        });

        quickModal.addEventListener('click', (e) => {
            if (e.target === quickModal) {
                quickModal.classList.remove('active');
                document.body.style.overflow = '';
            }
        });
    }

    // 4. Lightbox
    const productImages = document.querySelectorAll('.image-item img');
    const lightbox = document.getElementById('lightbox');

    if (productImages.length > 0 && lightbox) {
        const lightboxImg = document.getElementById('lightbox-img');
        const lightboxCaption = document.querySelector('.lightbox-caption');
        const closeLightbox = document.querySelector('.lightbox-close');

        productImages.forEach(img => {
            img.closest('.image-item').addEventListener('click', () => {
                lightboxImg.src = img.src;
                lightboxCaption.textContent = img.alt;
                lightbox.classList.add('active');
                document.body.style.overflow = 'hidden';
            });
        });

        closeLightbox.addEventListener('click', () => {
            lightbox.classList.remove('active');
            document.body.style.overflow = '';
        });

        lightbox.addEventListener('click', (e) => {
            if (e.target === lightbox) {
                lightbox.classList.remove('active');
                document.body.style.overflow = '';
            }
        });
    }

    console.log('✨ Products Page Interactions Initialized');

    // ============================================
    // MORE INFO PAGE ANIMATIONS
    // ============================================

    // 1. Radial Progress Animation
    const radialProgressBars = document.querySelectorAll('.radial-progress');
    if (radialProgressBars.length > 0) {
        const progressObserver = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    const progress = entry.target;
                    const circle = progress.querySelector('.progress-ring__circle');
                    const percentage = progress.dataset.percentage;

                    // Circle Animation
                    const radius = circle.r.baseVal.value;
                    const circumference = radius * 2 * Math.PI;
                    circle.style.strokeDasharray = `${circumference} ${circumference}`;
                    circle.style.strokeDashoffset = circumference;

                    const offset = circumference - (percentage / 100) * circumference;
                    setTimeout(() => {
                        circle.style.strokeDashoffset = offset;
                    }, 100);

                    // Number Count Up
                    const numberEl = progress.querySelector('.stat-number');
                    const target = parseInt(numberEl.dataset.target);
                    // Check if it's the 5 star one to keep decimal if needed, but here integers
                    let start = 0;
                    const duration = 2000;
                    const stepTime = 20;
                    const steps = duration / stepTime;
                    const increment = target / steps;

                    const timer = setInterval(() => {
                        start += increment;
                        if (start >= target) {
                            numberEl.textContent = target + (target === 5 ? '★' : '+'); // Add suffix back
                            clearInterval(timer);
                        } else {
                            numberEl.textContent = Math.floor(start) + (target === 5 ? '★' : '+');
                        }
                    }, stepTime);

                    progressObserver.unobserve(entry.target);
                }
            });
        }, { threshold: 0.5 });

        radialProgressBars.forEach(bar => progressObserver.observe(bar));
    }

    // 2. Expandable Info Cards
    const expandableCards = document.querySelectorAll('.info-card.expandable');
    expandableCards.forEach(card => {
        const header = card.querySelector('.info-card-header');
        header.addEventListener('click', () => {
            // Close others 
            expandableCards.forEach(c => {
                if (c !== card) c.classList.remove('active');
            });
            // Toggle current
            card.classList.toggle('active');
        });
    });

    // 3. Real-time Status
    const statusBadge = document.getElementById('business-status');
    if (statusBadge) {
        const now = new Date();
        const day = now.getDay(); // 0 is Sunday
        const hour = now.getHours();
        const minutes = now.getMinutes();

        // Define Hours: Mon-Sat 10-20, Sun 11-18
        let isOpen = false;

        if (day === 0) { // Sunday
            if (hour >= 11 && hour < 18) isOpen = true;
        } else { // Mon-Sat
            if (hour >= 10 && hour < 20) isOpen = true;
        }

        if (isOpen) {
            statusBadge.textContent = 'Open Now';
            statusBadge.className = 'status-badge open';
        } else {
            statusBadge.textContent = 'Closed';
            statusBadge.className = 'status-badge closed';
        }
    }

    // 4. Show on Map Button
    const mapBtn = document.getElementById('show-map-btn');
    if (mapBtn) {
        mapBtn.addEventListener('click', () => {
            const mapContainer = document.getElementById('map-container');
            mapContainer.scrollIntoView({ behavior: 'smooth', block: 'center' });

            // Allow scroll to finish then animate
            setTimeout(() => {
                mapContainer.classList.add('highlight');
                setTimeout(() => {
                    mapContainer.classList.remove('highlight');
                }, 1000);
            }, 800);
        });
    }

    console.log('✨ More Info Page Interactions Initialized');

    // ============================================
    // CONTACT PAGE ANIMATIONS
    // ============================================

    // 1. Floating Labels & Validation
    const formInputs = document.querySelectorAll('.form-group.floating .form-control');

    formInputs.forEach(input => {
        // Floating Label Logic check on load
        if (input.value.trim() !== '') {
            input.classList.add('has-value');
        }

        // Real-time Validation
        input.addEventListener('input', () => {
            if (input.checkValidity()) {
                input.classList.add('valid');
                input.classList.remove('invalid');
            } else {
                input.classList.add('invalid');
                input.classList.remove('valid');
            }
        });

        input.addEventListener('blur', () => {
            if (input.value.trim() === '') {
                input.classList.remove('valid');
                input.classList.remove('invalid');
            }
        });
    });

    // 2. Animated Form Submission
    const contactForm = document.getElementById('contact-form');
    if (contactForm) {
        contactForm.addEventListener('submit', (e) => {
            e.preventDefault();

            const btn = contactForm.querySelector('.btn-submit');

            // 1. Loading State
            btn.classList.add('loading');

            // Simulate API call
            setTimeout(() => {
                // 2. Success State
                btn.classList.remove('loading');
                btn.classList.add('success');

                // 3. Show Thank You Message
                setTimeout(() => {
                    contactForm.style.display = 'none';
                    const thankYou = document.getElementById('thank-you-message');
                    thankYou.style.display = 'block';

                    // Fire Confetti (CSS-only approximation or simple JS effect)
                    createConfetti();
                }, 1000);
            }, 2000);
        });
    }

    // Simple Confetti Effect
    function createConfetti() {
        const colors = ['#D32F2F', '#FF6F00', '#FFD700', '#2E7D32', '#1877f2'];
        for (let i = 0; i < 50; i++) {
            const confetti = document.createElement('div');
            confetti.style.position = 'fixed';
            confetti.style.width = '10px';
            confetti.style.height = '10px';
            confetti.style.backgroundColor = colors[Math.floor(Math.random() * colors.length)];
            confetti.style.left = '50%';
            confetti.style.top = '50%';
            confetti.style.zIndex = '9999';
            confetti.style.opacity = '1';
            document.body.appendChild(confetti);

            const angle = Math.random() * Math.PI * 2;
            const velocity = 5 + Math.random() * 5;
            const tx = Math.cos(angle) * velocity * 20;
            const ty = Math.sin(angle) * velocity * 20;

            confetti.animate([
                { transform: `translate(0, 0) rotate(0)` },
                { transform: `translate(${tx}px, ${ty}px) rotate(720deg)`, opacity: 0 }
            ], {
                duration: 1000 + Math.random() * 1000,
                easing: 'cubic-bezier(0.25, 0.46, 0.45, 0.94)'
            }).onfinish = () => confetti.remove();
        }
    }

    console.log('✨ Contact Page Interactions Initialized');
    // ============================================
    // NAVIGATION ENHANCEMENTS
    // ============================================

    // ============================================
    // NAVIGATION ENHANCEMENTS (Integrated)
    // ============================================

    if (header) {
        window.addEventListener('scroll', () => {
            if (window.scrollY > 50) {
                header.classList.add('scrolled');
            } else {
                header.classList.remove('scrolled');
            }
        });
    }

    if (mobileToggle && navMenu) {
        // Re-using variables declared at top
        mobileToggle.addEventListener('click', (e) => {
            e.stopPropagation();
            mobileToggle.classList.toggle('active');
            navMenu.classList.toggle('active');
            document.body.style.overflow = navMenu.classList.contains('active') ? 'hidden' : '';
        });

        // Close menu when clicking a link
        document.querySelectorAll('.nav-link').forEach(link => {
            link.addEventListener('click', () => {
                mobileToggle.classList.remove('active');
                navMenu.classList.remove('active');
                document.body.style.overflow = '';
            });
        });

        // Close menu when clicking outside
        document.addEventListener('click', (e) => {
            if (navMenu.classList.contains('active') && !navMenu.contains(e.target) && !mobileToggle.contains(e.target)) {
                mobileToggle.classList.remove('active');
                navMenu.classList.remove('active');
                document.body.style.overflow = '';
            }
        });
    }

    console.log('✨ Navigation Interactions Initialized');

    // ============================================
    // FOOTER ENHANCEMENTS
    // ============================================

    // 1. Back to Top Button
    // Injection logic moved here if needed, but we keep it separated for clarity
    const backToTop = document.querySelector('.back-to-top');
    if (backToTop) {
        window.addEventListener('scroll', () => {
            if (window.scrollY > 300) {
                backToTop.classList.add('visible');
            } else {
                backToTop.classList.remove('visible');
                backToTop.classList.remove('launch');
            }
        });

        backToTop.addEventListener('click', () => {
            backToTop.classList.add('launch');
            setTimeout(() => {
                window.scrollTo({ top: 0, behavior: 'smooth' });
            }, 300);
        });
    }

    console.log('✨ Footer Interactions Initialized');

    // ============================================
    // GLOBAL EFFECTS
    // ============================================

    // 1. Scroll Progress Bar
    const scrollProgressBar = document.querySelector('.scroll-progress-bar');
    if (scrollProgressBar) {
        window.addEventListener('scroll', () => {
            const scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
            const scrollHeight = document.documentElement.scrollHeight - document.documentElement.clientHeight;
            const scrollPercent = (scrollTop / scrollHeight) * 100;
            scrollProgressBar.style.width = scrollPercent + '%';
        });
    }

    // 2. Reveal Animations on Scroll
    const globalObserverOptions = {
        threshold: 0.15,
        rootMargin: "0px 0px -50px 0px"
    };

    const revealObserver = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('active');
                revealObserver.unobserve(entry.target);
            }
        });
    }, globalObserverOptions);

    document.querySelectorAll('.reveal-section, .reveal-left, .reveal-right').forEach(el => {
        revealObserver.observe(el);
    });

    // Add reveal classes to main sections if not already present
    document.querySelectorAll('section').forEach(section => {
        if (!section.classList.contains('reveal-section')) {
            section.classList.add('reveal-section');
            revealObserver.observe(section);
        }
    });

    // 3. Page Loader (Diya Animation)
    // Inject Loader HTML
    if (!document.querySelector('.page-loader')) {
        const loader = document.createElement('div');
        loader.className = 'page-loader';
        loader.innerHTML = `
            <div class="loader-content">
                <div class="diya-loader">
                    <div class="diya-flame"></div>
                </div>
                <div class="loader-text">Loading...</div>
            </div>
        `;
        document.body.appendChild(loader);

        // Hide loader after load
        window.addEventListener('load', () => {
            setTimeout(() => {
                loader.classList.add('hidden');
                setTimeout(() => {
                    loader.remove(); // Remove from DOM after transition
                }, 500);
            }, 800); // Small delay to show animation
        });
    }

    // 4. Custom Cursor
    if (window.matchMedia("(min-width: 992px)").matches) {
        // Inject Cursor HTML
        if (!document.querySelector('.cursor-dot')) {
            const cursorDot = document.createElement('div');
            cursorDot.className = 'cursor-dot';
            const cursorOutline = document.createElement('div');
            cursorOutline.className = 'cursor-outline';
            document.body.appendChild(cursorDot);
            document.body.appendChild(cursorOutline);

            // Movement Logic
            window.addEventListener('mousemove', (e) => {
                const posX = e.clientX;
                const posY = e.clientY;

                // Dot follows immediately
                cursorDot.style.left = `${posX}px`;
                cursorDot.style.top = `${posY}px`;

                // Outline follows with slight delay (via CSS transition, but we update pos)
                // Using animate for smoother lag
                cursorOutline.animate({
                    left: `${posX}px`,
                    top: `${posY}px`
                }, { duration: 500, fill: "forwards" });

                // Sparkle Trail
                if (Math.random() > 0.8) { // Only create sometimes
                    createSparkle(posX, posY);
                }
            });

            // Hover Effects
            document.querySelectorAll('a, button, .btn, .interactive').forEach(el => {
                el.addEventListener('mouseenter', () => {
                    cursorOutline.style.transform = 'translate(-50%, -50%) scale(1.5)';
                    cursorOutline.style.backgroundColor = 'rgba(255, 215, 0, 0.1)';
                    cursorDot.style.transform = 'translate(-50%, -50%) scale(0.5)';
                });

                el.addEventListener('mouseleave', () => {
                    cursorOutline.style.transform = 'translate(-50%, -50%) scale(1)';
                    cursorOutline.style.backgroundColor = 'transparent';
                    cursorDot.style.transform = 'translate(-50%, -50%) scale(1)';
                });
            });

            // Click Burst
            document.addEventListener('click', (e) => {
                createBurst(e.clientX, e.clientY);
            });
        }
    }

    function createSparkle(x, y) {
        const sparkle = document.createElement('div');
        sparkle.className = 'cursor-trail';
        sparkle.style.left = `${x}px`;
        sparkle.style.top = `${y}px`;
        document.body.appendChild(sparkle);

        setTimeout(() => {
            sparkle.remove();
        }, 800);
    }

    function createBurst(x, y) {
        const colors = ['#FFD700', '#FF6F00', '#D32F2F'];
        for (let i = 0; i < 8; i++) {
            const particle = document.createElement('div');
            particle.className = 'cursor-trail';
            particle.style.backgroundColor = colors[Math.floor(Math.random() * colors.length)];
            particle.style.left = `${x}px`;
            particle.style.top = `${y}px`;
            particle.style.width = '4px';
            particle.style.height = '4px';
            document.body.appendChild(particle);

            const angle = (Math.PI * 2 * i) / 8;
            const velocity = 30;
            const tx = Math.cos(angle) * velocity;
            const ty = Math.sin(angle) * velocity;

            particle.animate([
                { transform: 'translate(0,0) scale(1)', opacity: 1 },
                { transform: `translate(${tx}px, ${ty}px) scale(0)`, opacity: 0 }
            ], {
                duration: 500,
                easing: 'ease-out'
            }).onfinish = () => particle.remove();
        }
    }

    // 5. Image Shimmer & Blur Load
    const images = document.querySelectorAll('img');
    images.forEach(img => {
        img.classList.add('loading');
        if (img.complete) {
            img.classList.add('loaded');
            img.classList.remove('loading');
        } else {
            img.addEventListener('load', () => {
                img.classList.add('loaded');
                img.classList.remove('loading');
            });
        }
    });

    // ============================================
    // SEASONAL FEATURES
    // ============================================

    function checkSeason() {
        const date = new Date();
        const month = date.getMonth(); // 0-11
        const day = date.getDate();
        const body = document.body;

        // Reset seasons
        body.classList.remove('season-wedding', 'season-diwali', 'season-newyear');

        // Logic (Priority: New Year > Diwali > Wedding)
        // For now, FORCE Wedding Season as requested by user
        const isWeddingSeason = true;

        if (isWeddingSeason) {
            activateWeddingMode();
        }
    }

    function activateWeddingMode() {
        document.body.classList.add('season-wedding');

        // Add Badge
        const hero = document.querySelector('.hero');
        if (hero && !document.querySelector('.wedding-badge')) {
            const badge = document.createElement('div');
            badge.className = 'wedding-badge';
            badge.innerHTML = '💍 Wedding Season Special';
            hero.appendChild(badge);
        }

        // Falling Rose Petals
        setInterval(createPetal, 300);
    }

    function createPetal() {
        const petal = document.createElement('div');
        petal.className = 'rose-petal';
        petal.style.left = Math.random() * 100 + 'vw';
        petal.style.animationDuration = Math.random() * 3 + 4 + 's'; // 4-7s
        petal.style.width = Math.random() * 10 + 10 + 'px';
        petal.style.height = petal.style.width;

        document.body.appendChild(petal);

        setTimeout(() => {
            petal.remove();
        }, 7000);
    }

    // Initialize Season Check
    checkSeason();

    console.log('✨ Seasonal Features Initialized');
    // ============================================
    // MOBILE OPTIMIZATIONS
    // ============================================

    // 1. Inject Bottom Navigation Bar
    if (window.matchMedia("(max-width: 768px)").matches && !document.querySelector('.mobile-bottom-nav')) {
        const bottomNav = document.createElement('div');
        bottomNav.className = 'mobile-bottom-nav';
        bottomNav.innerHTML = `
            <a href="index.html" class="mobile-nav-item ${window.location.pathname.includes('index.html') || window.location.pathname.endsWith('/') ? 'active' : ''}">
                <i class="fas fa-home"></i>
                <span>Home</span>
            </a>
            <a href="products.html" class="mobile-nav-item ${window.location.pathname.includes('products.html') ? 'active' : ''}">
                <i class="fas fa-box-open"></i>
                <span>Products</span>
            </a>
            <a href="contact.html" class="mobile-nav-item ${window.location.pathname.includes('contact.html') ? 'active' : ''}">
                <i class="fas fa-envelope"></i>
                <span>Contact</span>
            </a>
            <a href="more-info.html" class="mobile-nav-item ${window.location.pathname.includes('more-info.html') ? 'active' : ''}">
                <i class="fas fa-map-marker-alt"></i>
                <span>Location</span>
            </a>
        `;
        document.body.appendChild(bottomNav);
    }

    // 2. Swipe Gestures
    let touchStartX = 0;
    let touchEndX = 0;

    document.addEventListener('touchstart', e => {
        touchStartX = e.changedTouches[0].screenX;
    });

    document.addEventListener('touchend', e => {
        touchEndX = e.changedTouches[0].screenX;
        handleSwipe();
    });

    function handleSwipe() {
        const swipeThreshold = 100;
        if (touchEndX < touchStartX - swipeThreshold) {
            // Swipe Left - Close Menu if open
            if (document.querySelector('.nav-menu.active')) {
                document.querySelector('.mobile-toggle').click();
            }
        }

        if (touchEndX > touchStartX + swipeThreshold) {
            // Swipe Right - Open Menu (Optional, currently disabled to prevent accidental opens)
            // if (!document.querySelector('.nav-menu.active')) {
            //     document.querySelector('.mobile-toggle').click();
            // }
        }
    }

    // ============================================
    // PERFORMANCE ENHANCEMENTS
    // ============================================

    // 1. Service Worker Registration
    if ('serviceWorker' in navigator) {
        window.addEventListener('load', () => {
            navigator.serviceWorker.register('./sw.js')
                .then(reg => console.log('🚀 Service Worker registered', reg))
                .catch(err => console.log('❌ Service Worker failed', err));
        });
    }

    // 2. Lazy Loading with Blur-up
    function initLazyLoading() {
        const blurDivs = document.querySelectorAll('.blur-load');

        const observerOptions = {
            root: null,
            threshold: 0,
            rootMargin: "0px 0px 200px 0px"
        };

        const imageObserver = new IntersectionObserver((entries, observer) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    const div = entry.target;
                    const img = div.querySelector('img');

                    if (img && img.dataset.src) {
                        img.src = img.dataset.src;
                        img.onload = () => {
                            div.classList.add('loaded');
                        };
                    }
                    observer.unobserve(div);
                }
            });
        }, observerOptions);

        blurDivs.forEach(div => imageObserver.observe(div));
    }

    // 3. Offline/Online Notifications
    function initOfflineTracking() {
        const offlineToast = document.createElement('div');
        offlineToast.className = 'offline-notification';
        offlineToast.innerHTML = `
            <i class="fas fa-wifi-slash offline-icon"></i>
            <span>You are currently offline. Showing cached content.</span>
        `;
        document.body.appendChild(offlineToast);

        window.addEventListener('offline', () => {
            offlineToast.classList.add('visible');
        });

        window.addEventListener('online', () => {
            offlineToast.classList.remove('visible');
        });
    }

    initLazyLoading();
    initOfflineTracking();

    // ============================================
    // INTERACTIVE ENGAGEMENT TOOLS
    // ============================================

    // 1. Live Chat Widget Injection & Logic
    function initLiveChat() {
        if (document.querySelector('.chat-widget')) return;

        const chatWidget = document.createElement('div');
        chatWidget.className = 'chat-widget';
        chatWidget.innerHTML = `
            <div class="chat-window">
                <div class="chat-header">
                    <span>Chat with Us</span>
                    <i class="fas fa-times chat-close" style="cursor:pointer"></i>
                </div>
                <div class="chat-body">
                    <p>Namaste! How can we help you celebrate today?</p>
                    <div class="chat-options">
                        <div class="chat-option" data-msg="Firecrackers Enquiry">Firecrackers enquiry</div>
                        <div class="chat-option" data-msg="Wedding Cards Portfolio">Wedding cards portfolio</div>
                        <div class="chat-option" data-msg="Store Timings">Store timings</div>
                    </div>
                </div>
            </div>
            <div class="chat-bubble">
                <i class="fas fa-comment-dots"></i>
            </div>
        `;
        document.body.appendChild(chatWidget);

        const bubble = chatWidget.querySelector('.chat-bubble');
        const window = chatWidget.querySelector('.chat-window');
        const close = chatWidget.querySelector('.chat-close');

        bubble.addEventListener('click', () => {
            window.classList.toggle('active');
        });

        close.addEventListener('click', () => {
            window.classList.remove('active');
        });

        chatWidget.querySelectorAll('.chat-option').forEach(option => {
            option.addEventListener('click', () => {
                const msg = encodeURIComponent(`Hi Jay Bhavani Enterprises, I have a query about: ${option.dataset.msg}`);
                window.open(`https://wa.me/919822000000?text=${msg}`, '_blank');
            });
        });
    }

    // 2. Global Search Injection & Logic
    function initGlobalSearch() {
        const headerContainer = document.querySelector('.header .container');
        if (!headerContainer || document.querySelector('.search-container')) return;

        const searchContainer = document.createElement('div');
        searchContainer.className = 'search-container';
        searchContainer.innerHTML = `
            <i class="fas fa-search search-trigger" style="cursor:pointer; color: var(--primary)"></i>
            <input type="text" class="search-input" placeholder="Search products...">
            <div class="search-results"></div>
        `;

        // Find nav and insert before it
        const nav = headerContainer.querySelector('.nav');
        if (nav) headerContainer.insertBefore(searchContainer, nav);
        else headerContainer.appendChild(searchContainer);

        const input = searchContainer.querySelector('.search-input');
        const trigger = searchContainer.querySelector('.search-trigger');
        const results = searchContainer.querySelector('.search-results');

        trigger.addEventListener('click', () => {
            searchContainer.classList.toggle('active');
            if (searchContainer.classList.contains('active')) input.focus();
        });

        // Mock data for search (In a real app, this would be a JSON file or API)
        const products = [
            { name: "Sivakasi Sparklers", cat: "Firecrackers", url: "products.html#firecrackers" },
            { name: "Sky Shots 120", cat: "Firecrackers", url: "products.html#firecrackers" },
            { name: "Floral Wedding Cards", cat: "Wedding", url: "products.html#wedding" },
            { name: "Royal Gold Invitation", cat: "Wedding", url: "products.html#wedding" },
            { name: "3D Birthday Pop-ups", cat: "Birthday", url: "products.html#birthday" }
        ];

        input.addEventListener('input', (e) => {
            const query = e.target.value.toLowerCase();
            results.innerHTML = '';

            if (query.length < 2) {
                results.style.display = 'none';
                return;
            }

            const filtered = products.filter(p => p.name.toLowerCase().includes(query) || p.cat.toLowerCase().includes(query));

            if (filtered.length > 0) {
                filtered.forEach(p => {
                    const item = document.createElement('div');
                    item.className = 'search-result-item';
                    const highlightedName = p.name.replace(new RegExp(query, 'gi'), match => `<span class="search-highlight">${match}</span>`);
                    item.innerHTML = `
                        <i class="fas fa-tag" style="color: #ccc; font-size: 0.8rem"></i>
                        <div>
                            <div style="font-weight:600; font-size:0.9rem">${highlightedName}</div>
                            <div style="font-size:0.75rem; color: #888">${p.cat}</div>
                        </div>
                    `;
                    item.addEventListener('click', () => window.location.href = p.url);
                    results.appendChild(item);
                });
                results.style.display = 'block';
            } else {
                results.innerHTML = '<div class="search-result-item">No results found</div>';
                results.style.display = 'block';
            }
        });

        document.addEventListener('click', (e) => {
            if (!searchContainer.contains(e.target)) {
                searchContainer.classList.remove('active');
                results.style.display = 'none';
            }
        });
    }

    // 3. Wishlist Management
    function initWishlist() {
        const wishlist = JSON.parse(localStorage.getItem('bbt_wishlist')) || [];

        // Apply active state on load
        document.querySelectorAll('.wishlist-btn').forEach(btn => {
            const id = btn.dataset.productId;
            if (wishlist.includes(id)) btn.classList.add('active');

            btn.addEventListener('click', (e) => {
                e.preventDefault();
                e.stopPropagation();
                btn.classList.toggle('active');

                const idx = wishlist.indexOf(id);
                if (btn.classList.contains('active')) {
                    if (idx === -1) wishlist.push(id);
                } else {
                    if (idx > -1) wishlist.splice(idx, 1);
                }
                localStorage.setItem('bbt_wishlist', JSON.stringify(wishlist));
            });
        });
    }

    // 4. Product Comparison
    function initComparison() {
        const compareNodes = document.querySelectorAll('.compare-checkbox');
        if (compareNodes.length === 0) return;

        let compareBar = document.querySelector('.compare-bar');
        if (!compareBar) {
            compareBar = document.createElement('div');
            compareBar.className = 'compare-bar';
            compareBar.innerHTML = `
                <div style="font-weight:700">Compare Products (<span class="compare-count">0</span>)</div>
                <button class="btn btn-primary btn-sm compare-now">Compare Now</button>
                <button class="btn btn-outline btn-sm compare-clear">Clear</button>
            `;
            document.body.appendChild(compareBar);
        }

        const countSpan = compareBar.querySelector('.compare-count');
        const compareBtn = compareBar.querySelector('.compare-now');
        const clearBtn = compareBar.querySelector('.compare-clear');

        function updateCompareBar() {
            const checked = document.querySelectorAll('.compare-checkbox:checked');
            countSpan.textContent = checked.length;
            if (checked.length > 0) compareBar.classList.add('active');
            else compareBar.classList.remove('active');
        }

        compareNodes.forEach(cb => {
            cb.addEventListener('change', updateCompareBar);
        });

        clearBtn.addEventListener('click', () => {
            compareNodes.forEach(cb => cb.checked = false);
            updateCompareBar();
        });

        compareBtn.addEventListener('click', () => {
            const checked = document.querySelectorAll('.compare-checkbox:checked');
            if (checked.length < 2) {
                alert("Please select at least 2 products to compare.");
                return;
            }
            showCompareModal();
        });
    }

    function showCompareModal() {
        let modal = document.querySelector('.compare-modal');
        if (!modal) {
            modal = document.createElement('div');
            modal.className = 'compare-modal';
            modal.innerHTML = `
                <div class="compare-content">
                    <i class="fas fa-times modal-close" style="position:absolute; top:20px; right:20px; cursor:pointer; font-size:1.5rem"></i>
                    <h2 style="margin-bottom:30px">Product Comparison</h2>
                    <div class="compare-table-wrapper">
                        <table class="compare-table">
                            <thead><tr class="compare-head"><th>Feature</th></tr></thead>
                            <tbody class="compare-body"></tbody>
                        </table>
                    </div>
                </div>
            `;
            document.body.appendChild(modal);
            modal.querySelector('.modal-close').addEventListener('click', () => modal.style.display = 'none');
        }

        modal.style.display = 'flex';
        // Logic to populate table would go here based on selected data-attrs
        // For now, a mock comparison
        const tbody = modal.querySelector('.compare-body');
        tbody.innerHTML = `
            <tr><td>Price</td><td>Premium</td><td>Eco</td></tr>
            <tr><td>Quality</td><td class="compare-diff">High</td><td>Standard</td></tr>
        `;
    }

    initLiveChat();
    initGlobalSearch();
    // Wishlist and Comparison need to be called on pages with products
    window.addEventListener('contentLoaded', () => {
        initWishlist();
        initComparison();
    });
    // Fallback for immediate call
    initWishlist();
    initComparison();

    // ============================================
    // TRUST & CONVERSION FEATURES
    // ============================================

    // 1. Reviews Carousel Logic
    function initReviews() {
        const carousel = document.querySelector('.reviews-carousel');
        if (!carousel) return;

        let index = 0;
        const cards = carousel.querySelectorAll('.review-card');
        const cardWidth = cards[0].offsetWidth + 30; // Including gap

        function moveCarousel() {
            index++;
            if (index > cards.length - 3) index = 0; // Adjust based on visible cards
            carousel.style.transform = `translateX(-${index * cardWidth}px)`;
        }

        setInterval(moveCarousel, 5000);
    }

    // 2. Trust Counters Logic
    function initCounters() {
        const counters = document.querySelectorAll('.counter-value');
        const speed = 200;

        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    const counter = entry.target;
                    const updateCount = () => {
                        const target = +counter.getAttribute('data-target');
                        const count = +counter.innerText;
                        const inc = target / speed;

                        if (count < target) {
                            counter.innerText = Math.ceil(count + inc);
                            setTimeout(updateCount, 1);
                        } else {
                            counter.innerText = target + (counter.getAttribute('data-plus') ? '+' : '');
                        }
                    };
                    updateCount();
                    observer.unobserve(counter);
                }
            });
        }, { threshold: 1 });

        counters.forEach(c => observer.observe(c));
    }

    // 3. Live Activity Popups
    function initLiveActivity() {
        const activities = [
            { user: "Rajesh from Kothrud", action: "just ordered Wedding Cards", product: "💌" },
            { user: "Priya from Hinjewadi", action: "just purchased Sparklers", product: "🎆" },
            { user: "Amit from Baner", action: "just enquired about Rockets", product: "🚀" },
            { user: "Snehal from Wakad", action: "just saved a Birthday Card", product: "🎂" }
        ];

        const popup = document.createElement('div');
        popup.className = 'live-activity';
        document.body.appendChild(popup);

        function showActivity() {
            const act = activities[Math.floor(Math.random() * activities.length)];
            popup.innerHTML = `
                <div class="activity-img">${act.product}</div>
                <div class="activity-text">
                    <div style="font-weight:700">${act.user}</div>
                    <div>${act.action}</div>
                    <div class="activity-time">Just now</div>
                </div>
            `;
            popup.classList.add('active');
            setTimeout(() => popup.classList.remove('active'), 5000);
        }

        // Show every 15-30 seconds
        setInterval(showActivity, 20000);
        setTimeout(showActivity, 5000); // Initial delay
    }

    // 4. Sticky CTA Visibility
    function initStickyCTA() {
        const cta = document.createElement('div');
        cta.className = 'sticky-cta';
        cta.innerHTML = `
            <div class="cta-vertical">
                <a href="https://wa.me/919822000000" class="btn btn-primary pulse-btn" style="padding: 15px; border-radius: 50px;">
                    <i class="fab fa-whatsapp"></i>
                </a>
                <a href="contact.html" class="btn btn-secondary" style="padding: 15px; border-radius: 50px;">
                    <i class="fas fa-envelope"></i>
                </a>
            </div>
        `;
        document.body.appendChild(cta);

        window.addEventListener('scroll', () => {
            if (window.scrollY > 500) cta.classList.add('visible');
            else cta.classList.remove('visible');
        });
    }

    // 5. Exit Intent Popup
    function initExitIntent() {
        if (sessionStorage.getItem('exit_popup_shown')) return;

        const popup = document.createElement('div');
        popup.className = 'exit-popup';
        popup.innerHTML = `
            <div class="exit-content">
                <button class="exit-close" aria-label="Close" title="Close">&times;</button>
                <div class="section-badge">Wait! Don't Miss Out</div>
                <h2 style="margin: 20px 0">Celebration Special Offer</h2>
                <div class="exit-offer">10% OFF</div>
                <p>On your first bulk order of Wedding Cards or Firecrackers!</p>
                <div style="margin-top: 30px">
                    <a href="contact.html" class="btn btn-primary">Claim My Discount</a>
                </div>
            </div>
        `;
        document.body.appendChild(popup);

        const close = popup.querySelector('.exit-close');
        close.addEventListener('click', () => {
            popup.classList.remove('active');
            sessionStorage.setItem('exit_popup_shown', 'true');
        });

        document.addEventListener('mouseleave', (e) => {
            if (e.clientY < 0 && !sessionStorage.getItem('exit_popup_shown')) {
                popup.classList.add('active');
            }
        });
    }

    initReviews();
    initCounters();
    initLiveActivity();
    initStickyCTA();
    initExitIntent();

    console.log('🛡️ Trust & Conversion Features Initialized');
    console.log('🤝 Interactive Features Initialized');
    console.log('⚡ Performance Features Initialized');
    console.log('✨ Mobile Features Initialized');
    console.log('✨ Global Effects Initialized');
});
