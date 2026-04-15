@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager manager;
    private final JwtHelper jwtHelper;

    public AuthController(UserDetailsService userDetailsService,
                          AuthenticationManager manager,
                          JwtHelper jwtHelper) {
        this.userDetailsService = userDetailsService;
        this.manager = manager;
        this.jwtHelper = jwtHelper;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){

        this.authenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.getUsername());

        String token = this.jwtHelper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .username(userDetails.getUsername())
                .token(token)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDetails> getUserDetails(
            @RequestHeader("Authorization") String tokenHeader) {

        String token = extractTokenFromHeader(tokenHeader);

        if (token != null) {
            String username = jwtHelper.getUserNameFromToken(token);
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            return ResponseEntity.ok(userDetails);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private String extractTokenFromHeader(String tokenHeader) {
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            return tokenHeader.substring(7);
        }
        return null;
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        try {
            manager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password");
        }
    }
}
