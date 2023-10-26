/*
* IDEAS:
* Use of multiple threads to increase speed of program - is 1 ray per thread too many threads
* either overwrite or extend some existing Javafx light to allow for manipulation of rays
* will need a view plane may need to pass the camera in as an argument*/

public class RayTracer {
    /*
    * Color ray_shade(int level, Real w, Ray v, RContext *rc, Object *ol){
    *   Inode *i = ray_intersect(ol, v);
    *   if (i != NULL) {
    *       Light *l; Real wf;
    *       Material *m = i->m;
    *       Vector3 p = ray_point(v, i->t) ;
    *       Cone recv = cone_make(p, i->n, PIOVER2);
    *       Color c = c_mult(m->c, c_scale(m->ka, ambient(rc)));
    *
    *       for (l = rc->l; l != NULL; l = l->next) {
    *           if ((*l->transport)(l, recv, rc) && (wf = shadow(l, p, ol))> RAY_WF_MIN)
    *               c = c_add(c, c_mult(m->c,c_scale(wf * m->kd * v3_dot(l->outdir,i->n), l->outcol)));
    *       }
    *
    *       if (level++ < MAX_RAY_LEVEL) {
    *           if ((wf = w * m->ks) > RAY_WF_MIN) {
    *               Ray r = ray_make(p, reflect_dir(v.d, i->n));
    *               c = c_add(c, c_mult(m->s,c_scale(m->ks, ray_shade(level, wf, r, rc, ol))));
    *               }
    *            if ((wf = w * m->kt) > RAY_WF_MIN) {
    *                Ray t = ray_make(p, refract_dir(v.d, i->n, (i->enter)?1/m->ir: m->ir));
    *                if (v3_sqrnorm(t.d) > 0) {
    *                   c = c_add(c, c_mult(m->s,c_scale(m->kt, ray_shade(level, wf, t, rc, ol))));
    *                }
    *            }
    *       }
    *           inode_free(i);
    *           return c;
    *   } else {
    *       return BG_COLOR;
    *       }
    *}*/
}
