    package com.jiwoo.MeetingRoom.presentation;

    import com.jiwoo.MeetingRoom.application.ReservationService;
    import com.jiwoo.MeetingRoom.domain.Reservation;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    public class ReservationController {

        @Autowired
        private ReservationService reservationService;

        public ReservationController(ReservationService reservationService) {
            this.reservationService = reservationService;
        }

        @RequestMapping(value = "/reservations", method = RequestMethod.POST)
        public Reservation createReservation(@RequestBody Reservation reservation) {
            return reservationService.add(reservation);
        }

        @RequestMapping(value = "/reservations/rooms/{id}",method = RequestMethod.GET)
        public List<Reservation> findByRoomId(@PathVariable Long id){
            return reservationService.findByRoomId(id);
        }

        @RequestMapping(value = "/reservations/{id}",method = RequestMethod.DELETE)
        public String deleteReservation(@PathVariable Long id, @RequestParam String password){
            boolean result = reservationService.delete(id,password);
            if (result){
                return "success";
            } else  {
                return "fail";
            }
        }

    }
